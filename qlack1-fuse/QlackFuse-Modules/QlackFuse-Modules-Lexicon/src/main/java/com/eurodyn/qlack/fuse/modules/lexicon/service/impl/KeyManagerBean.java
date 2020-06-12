package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.commons.search.ApplyPagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexDataDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexKeyDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexData;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexGroup;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexKey;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexLanguage;
import com.eurodyn.qlack.fuse.modules.lexicon.service.KeyManager;
import com.eurodyn.qlack.fuse.modules.lexicon.service.LanguageManager;
import com.eurodyn.qlack.fuse.modules.lexicon.util.ConverterUtil;
import com.eurodyn.qlack.fuse.modules.lexicon.util.LexiconValidationUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.Order;
import net.bzdyl.ejb3.criteria.restrictions.MatchMode;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A Stateless Session EJB and also web service implementation class providing methods for key management like CRUD
 * operation for Key and Lexicon Key Mapping table.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Stateless(name = "KeyManagerBean")
public class KeyManagerBean implements KeyManager {

  @PersistenceContext(unitName = "QlackFuse-Lexicon-PU")
  private EntityManager em;

  @EJB(name = "LanguageManagerBean")
  private LanguageManager langManager;

  private static final Logger logger = Logger.getLogger(KeyManagerBean.class.getName());

  /**
   * {@inheritDoc}
   *
   * @param lexKeyDTO {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public String createKey(LexKeyDTO lexKeyDTO) throws QlackFuseLexiconException {
    // Create the new key.
    LexKey lexKey = new LexKey();
    lexKey.setCreatedBy(lexKeyDTO.getCreatedBy());
    lexKey.setCreatedOn(lexKeyDTO.getCreatedOn());
    if (!StringUtils.isEmpty(lexKeyDTO.getGroupId())) {
      lexKey.setGroupId(em.find(LexGroup.class, lexKeyDTO.getGroupId()));
    }
    lexKey.setName(lexKeyDTO.getName());
    em.persist(lexKey);

    // Create default data values for this key.
    LexLanguageDTO[] lgDTOs = langManager.listLanguages(null, true);
    for (LexLanguageDTO lgDTO : lgDTOs) {
      LexData lexData = new LexData();
      lexData.setCreatedBy(lexKeyDTO.getCreatedBy());
      lexData.setCreatedOn(lexKeyDTO.getCreatedOn());
      lexData.setApproved(true);
      lexData.setLanguageId(em.find(LexLanguage.class, lgDTO.getId()));
      lexData.setValue(lexKeyDTO.getName());
      lexData.setKeyId(lexKey);
      em.persist(lexData);
    }
    em.flush();

    // And finally, update any translations that may have been provided by the user.
    if (lexKeyDTO.getData() != null) {
      updateTranslationsByKeyID(lexKey.getId(), lexKeyDTO.getData(),
          lexKeyDTO.getCreatedBy());
    }

    return lexKey.getId();
  }

  private LexKey getKeyByName(String keyName) {
    Criteria criteria = CriteriaFactory.createCriteria("LexKey");
    criteria.add(Restrictions.eq("name", keyName));

    return (LexKey) criteria.prepareQuery(em).getSingleResult();
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   * @param locale {@inheritDoc}
   * @param value {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void updateTranslationByKeyName(String keyName, String locale, String value,
      String modifiedBy) throws QlackFuseLexiconException {
    updateTranslationByKey(getKeyByName(keyName), locale, value, modifiedBy);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyID {@inheritDoc}
   * @param locale {@inheritDoc}
   * @param value {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void updateTranslationByKeyID(String keyID, String locale, String value,
      String modifiedBy) throws QlackFuseLexiconException {
    updateTranslationByKey(em.find(LexKey.class, keyID), locale, value, modifiedBy);
  }

  private void updateTranslationByKey(LexKey key, final String locale, final String value,
      String modifiedBy) throws QlackFuseLexiconException {
    Map m = new HashMap();
    m.put(locale, value);
    updateTranslationsByKey(key, m, modifiedBy);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   * @param lDTOs {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void updateTranslationsByKeyNameWithDTO(String keyName, LexDataDTO[] lDTOs,
      String modifiedBy)
      throws QlackFuseLexiconException {
    Map<String, String> translations = new HashMap<String, String>();
    for (LexDataDTO lDTO : lDTOs) {
      translations.put(lDTO.getLocale(), lDTO.getValue());
    }
    updateTranslationsByKey(getKeyByName(keyName), translations, modifiedBy);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyID {@inheritDoc}
   * @param lDTOs {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void updateTranslationsByKeyID(String keyID, LexDataDTO[] lDTOs, String modifiedBy)
      throws QlackFuseLexiconException {
    Map<String, String> translations = new HashMap<String, String>();
    for (LexDataDTO lDTO : lDTOs) {
      translations.put(lDTO.getLocale(), lDTO.getValue());
    }
    updateTranslationsByKey(em.find(LexKey.class, keyID), translations, modifiedBy);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   * @param data {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void updateTranslationsByKeyName(String keyName, HashMap<String, String> data,
      String modifiedBy) throws QlackFuseLexiconException {
    updateTranslationsByKey(getKeyByName(keyName), data, modifiedBy);
  }

  private void updateTranslationsByKey(LexKey key, Map<String, String> data,
      String modifiedBy) throws QlackFuseLexiconException {
    for (Iterator<String> localeI = data.keySet().iterator(); localeI.hasNext(); ) {
      Criteria criteria = CriteriaFactory.createCriteria("LexData");
      criteria.createAlias("keyId", "key");
      criteria.add(Restrictions.eq("key.id", key.getId()));
      String locale = localeI.next();
      criteria.createAlias("languageId", "lg");
      criteria.add(Restrictions.eq("lg.locale", locale));
      LexData lexData = (LexData) criteria.prepareQuery(em).getSingleResult();
      lexData.setLastModifiedBy(modifiedBy);
      lexData.setLastModifiedOn(System.currentTimeMillis());
      lexData.setValue(data.get(locale));
      em.persist(lexData);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void deleteKeyByName(String keyName) throws QlackFuseLexiconException {
    deleteKey(getKeyByName(keyName));
  }

  /**
   * {@inheritDoc}
   *
   * @param keyID {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void deleteKeyByID(String keyID) throws QlackFuseLexiconException {
    deleteKey(em.find(LexKey.class, keyID));
  }

  private void deleteKey(LexKey key) throws QlackFuseLexiconException {
    em.remove(key);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyID {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexKeyDTO viewKeyByID(String keyID) throws QlackFuseLexiconException {
    LexiconValidationUtil.validateNullForKeyID(keyID);
    LexKey key = em.find(LexKey.class, keyID);

    return ConverterUtil.lexKeyToLexKeyDTO(key);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexKeyDTO viewKeyByName(String keyName) throws QlackFuseLexiconException {
    return ConverterUtil.lexKeyToLexKeyDTO(getKeyByName(keyName));
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   * @param newName {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void renameKeyByName(String keyName, String newName, String modifiedBy)
      throws QlackFuseLexiconException {
    renameKey(getKeyByName(keyName), newName, modifiedBy);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyID {@inheritDoc}
   * @param newName {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void renameKeyByID(String keyID, String newName, String modifiedBy)
      throws QlackFuseLexiconException {
    renameKey(em.find(LexKey.class, keyID), newName, modifiedBy);
  }

  private void renameKey(LexKey key, String newName, String modifiedBy) {
    key.setName(newName);
    key.setLastModifiedBy(modifiedBy);
    key.setLastModifiedOn(System.currentTimeMillis());
  }

  /**
   * {@inheritDoc}
   *
   * @param groupId {@inheritDoc}
   * @param paging {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public LexKeyDTO[] listKeysByGroupID(String groupId, PagingParams paging) {
    String groupClause = "";
    if (groupId != null) {
      groupClause = "where k.groupId.id = :groupId";
    }
    Query query = em.createQuery("select k from LexKey k " + groupClause + " order by k.name");
    if (groupId != null) {
      query.setParameter("groupId", groupId);
    }
    query = ApplyPagingParams.apply(query, paging);
    List<LexKeyDTO> retVal = new ArrayList<LexKeyDTO>();
    for (Iterator<LexKey> lexKeyI = query.getResultList().iterator(); lexKeyI.hasNext(); ) {
      retVal.add(ConverterUtil.lexKeyToLexKeyDTO(lexKeyI.next()));
    }

    return retVal.toArray(new LexKeyDTO[retVal.size()]);
  }

  /**
   * {@inheritDoc}
   *
   * @param groupId {@inheritDoc}
   * @param searchTerm {@inheritDoc}
   * @param paging {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexKeyDTO[] searchKeys(String groupId, String searchTerm, PagingParams paging)
      throws QlackFuseLexiconException {
    LexiconValidationUtil.validateNullForSearchTerm(searchTerm);

    StringBuilder quString = new StringBuilder("select key from LexKey key where ");
    if (groupId != null) {
      quString.append("key.groupId.id = :groupId and ");
    }
    quString.append("UPPER(key.name) like :nameSearchTerm ");
    Query query = em.createQuery(quString.toString());
    if (groupId != null) {
      query.setParameter("groupId", groupId);
    }
    query.setParameter("nameSearchTerm", "%" + searchTerm.toUpperCase() + "%");
    query = ApplyPagingParams.apply(query, paging);
    List<LexKeyDTO> retVal = new ArrayList<LexKeyDTO>();
    for (Iterator<LexKey> lexKeyI = query.getResultList().iterator(); lexKeyI.hasNext(); ) {
      retVal.add(ConverterUtil.lexKeyToLexKeyDTO(lexKeyI.next()));
    }

    return retVal.toArray(new LexKeyDTO[retVal.size()]);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public HashMap<String, String> getTranslationsByKeyName(String keyName) {
    HashMap<String, String> retVal = new HashMap<String, String>();

    Criteria criteria = CriteriaFactory.createCriteria("LexData");
    criteria.createAlias("languageId", "lg");
    criteria.addOrder(Order.ascending("lg.name"));
    criteria.createAlias("keyId", "key");
    criteria.add(Restrictions.eq("key.name", keyName));
    List l = criteria.prepareQuery(em).getResultList();
    for (Iterator<LexData> i = l.iterator(); i.hasNext(); ) {
      LexData lexData = i.next();
      retVal.put(lexData.getLanguageId().getLocale(), lexData.getValue());
    }

    return retVal;
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @param filter {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public HashMap<String, String> getTranslationsForLocale(String locale, String filter) {
    HashMap<String, String> retVal = new LinkedHashMap<String, String>();

    Criteria criteria = CriteriaFactory.createCriteria("LexData");
    criteria.createAlias("keyId", "key");
    criteria.addOrder(Order.ascending("key.name"));
    criteria.createAlias("languageId", "lg");
    criteria.add(Restrictions.eq("lg.locale", locale));
    if (!StringUtils.isEmpty(filter)) {
      criteria.add(Restrictions.or(Restrictions.like("key.name", filter, MatchMode.ANYWHERE),
          Restrictions.like("value", filter, MatchMode.ANYWHERE)));
    }
    List l = criteria.prepareQuery(em).getResultList();
    for (Iterator<LexData> i = l.iterator(); i.hasNext(); ) {
      LexData lexData = i.next();
      retVal.put(lexData.getKeyId().getName(), lexData.getValue());
    }

    return retVal;
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @param values {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   */
  @Override
  public void updateTranslationsForLocale(String locale, HashMap<String, String> values,
      String modifiedBy) {
    for (Iterator<String> keyI = values.keySet().iterator(); keyI.hasNext(); ) {
      Criteria criteria = CriteriaFactory.createCriteria("LexData");
      criteria.createAlias("languageId", "lg");
      criteria.add(Restrictions.eq("lg.locale", locale));
      String key = keyI.next();
      criteria.createAlias("keyId", "key");
      criteria.add(Restrictions.eq("key.name", key));
      LexData lexData = (LexData) criteria.prepareQuery(em).getSingleResult();
      lexData.setValue(values.get(key));
      lexData.setLastModifiedBy(modifiedBy);
      em.persist(lexData);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @param values {@inheritDoc}
   * @param createdBy {@inheritDoc}
   */
  @Override
  public void addTranslationsForLocale(String locale, HashMap<String, String> values,
      String createdBy) {
    Criteria criteria = CriteriaFactory.createCriteria("LexLanguage");
    criteria.add(Restrictions.eq("locale", locale));
    LexLanguage lexLanguage = (LexLanguage) criteria.prepareQuery(em).getSingleResult();

    em.setFlushMode(FlushModeType.COMMIT);
    for (Iterator<String> keyI = values.keySet().iterator(); keyI.hasNext(); ) {
      String key = keyI.next();
      LexData lexData = new LexData();
      lexData.setKeyId(getKeyByName(key));
      lexData.setLanguageId(lexLanguage);
      lexData.setCreatedBy(createdBy);
      lexData.setCreatedOn(System.currentTimeMillis());
      lexData.setApproved(true);
      lexData.setValue(values.get(key));
      em.persist(lexData);
    }
  }


  /**
   * {@inheritDoc}
   *
   * @param key {@inheritDoc}
   * @param locale {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public String getTranslationForKeyAndLocale(String key, String locale) {
    String retVal = key;

    Criteria criteria = CriteriaFactory.createCriteria("LexData");
    criteria.createAlias("languageId", "lg");
    criteria.add(Restrictions.eq("lg.locale", locale));
    criteria.createAlias("keyId", "key");
    criteria.add(Restrictions.eq("key.name", key));

    try {
      LexData lexData = (LexData) criteria.prepareQuery(em).getSingleResult();
      retVal = lexData.getValue();
    } catch (NoResultException ex) {
      // do nothinh
    }

    return retVal;
  }


  /**
   * {@inheritDoc}
   *
   * @param groupId {@inheritDoc}
   * @param locale {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public HashMap<String, String> getTranslationsForGroupAndLocale(String groupId, String locale) {
    HashMap<String, String> retVal = new LinkedHashMap<String, String>();

    Query query = em.createQuery("SELECT d FROM LexData d WHERE d.languageId.locale = :locale "
        + "AND d.keyId.groupId.id = :groupId");

    query.setParameter("locale", locale);
    query.setParameter("groupId", groupId);

    @SuppressWarnings("unchecked")
    List<LexData> l = query.getResultList();
    for (Iterator<LexData> i = l.iterator(); i.hasNext(); ) {
      LexData lexData = i.next();
      retVal.put(lexData.getKeyId().getName(), lexData.getValue());
    }

    return retVal;
  }

}
