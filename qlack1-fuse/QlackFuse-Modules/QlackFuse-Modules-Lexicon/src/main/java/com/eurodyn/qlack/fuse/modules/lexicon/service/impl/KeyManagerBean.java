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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

  /**
   * {@inheritDoc}
   *
   * @param lexKeyDTO {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public String createKey(LexKeyDTO lexKeyDTO) {
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

  protected LexKey getKeyByName(String keyName) {
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
   */
  @Override
  public void updateTranslationByKeyName(String keyName, String locale, String value,
      String modifiedBy) {
    updateTranslationByKey(getKeyByName(keyName), locale, value, modifiedBy);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyID {@inheritDoc}
   * @param locale {@inheritDoc}
   * @param value {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   */
  @Override
  public void updateTranslationByKeyID(String keyID, String locale, String value,
      String modifiedBy) {
    updateTranslationByKey(em.find(LexKey.class, keyID), locale, value, modifiedBy);
  }

  private void updateTranslationByKey(LexKey key, final String locale, final String value,
      String modifiedBy) {
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
   */
  @Override
  public void updateTranslationsByKeyNameWithDTO(String keyName, LexDataDTO[] lDTOs,
      String modifiedBy) {
    Map<String, String> translations = new HashMap<>();
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
   */
  @Override
  public void updateTranslationsByKeyID(String keyID, LexDataDTO[] lDTOs, String modifiedBy) {
    Map<String, String> translations = new HashMap<>();
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
   */
  @Override
  public void updateTranslationsByKeyName(String keyName, HashMap<String, String> data,
      String modifiedBy) {
    updateTranslationsByKey(getKeyByName(keyName), data, modifiedBy);
  }

  private void updateTranslationsByKey(LexKey key, Map<String, String> data,
      String modifiedBy) {
    for (Entry<String, String> s : data.entrySet()) {
      Criteria criteria = CriteriaFactory.createCriteria("LexData");
      criteria.createAlias("keyId", "key");
      criteria.add(Restrictions.eq("key.id", key.getId()));
      criteria.createAlias("languageId", "lg");
      criteria.add(Restrictions.eq("lg.locale", s.getKey()));
      LexData lexData = (LexData) criteria.prepareQuery(em).getSingleResult();
      lexData.setLastModifiedBy(modifiedBy);
      lexData.setLastModifiedOn(System.currentTimeMillis());
      lexData.setValue(data.get(s.getKey()));
      em.persist(lexData);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   */
  @Override
  public void deleteKeyByName(String keyName) {
    deleteKey(getKeyByName(keyName));
  }

  /**
   * {@inheritDoc}
   *
   * @param keyID {@inheritDoc}
   */
  @Override
  public void deleteKeyByID(String keyID) {
    deleteKey(em.find(LexKey.class, keyID));
  }

  private void deleteKey(LexKey key) {
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
   */
  @Override
  public LexKeyDTO viewKeyByName(String keyName) {
    return ConverterUtil.lexKeyToLexKeyDTO(getKeyByName(keyName));
  }

  /**
   * {@inheritDoc}
   *
   * @param keyName {@inheritDoc}
   * @param newName {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   */
  @Override
  public void renameKeyByName(String keyName, String newName, String modifiedBy) {
    renameKey(getKeyByName(keyName), newName, modifiedBy);
  }

  /**
   * {@inheritDoc}
   *
   * @param keyID {@inheritDoc}
   * @param newName {@inheritDoc}
   * @param modifiedBy {@inheritDoc}
   */
  @Override
  public void renameKeyByID(String keyID, String newName, String modifiedBy) {
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
    List<LexKeyDTO> retVal = new ArrayList<>();
    for (LexKey o : (Iterable<LexKey>) query.getResultList()) {
      retVal.add(ConverterUtil.lexKeyToLexKeyDTO(o));
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
    List<LexKeyDTO> retVal = new ArrayList<>();
    for (LexKey o : (Iterable<LexKey>) query.getResultList()) {
      retVal.add(ConverterUtil.lexKeyToLexKeyDTO(o));
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
    HashMap<String, String> retVal = new HashMap<>();

    Criteria criteria = CriteriaFactory.createCriteria("LexData");
    criteria.createAlias("languageId", "lg");
    criteria.addOrder(Order.ascending("lg.name"));
    criteria.createAlias("keyId", "key");
    criteria.add(Restrictions.eq("key.name", keyName));
    List l = criteria.prepareQuery(em).getResultList();
    for (LexData lexData : (Iterable<LexData>) l) {
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
    HashMap<String, String> retVal = new LinkedHashMap<>();

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
    for (LexData lexData : (Iterable<LexData>) l) {
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
    for (String s : values.keySet()) {
      Criteria criteria = CriteriaFactory.createCriteria("LexData");
      criteria.createAlias("languageId", "lg");
      criteria.add(Restrictions.eq("lg.locale", locale));
      String key = s;
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
    for (Entry<String, String> key : values.entrySet()) {
      LexData lexData = new LexData();
      lexData.setKeyId(getKeyByName(key.getKey()));
      lexData.setLanguageId(lexLanguage);
      lexData.setCreatedBy(createdBy);
      lexData.setCreatedOn(System.currentTimeMillis());
      lexData.setApproved(true);
      lexData.setValue(values.get(key.getKey()));
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
      // do nothing
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
    HashMap<String, String> retVal = new LinkedHashMap<>();

    Query query = em.createQuery("SELECT d FROM LexData d WHERE d.languageId.locale = :locale "
        + "AND d.keyId.groupId.id = :groupId");

    query.setParameter("locale", locale);
    query.setParameter("groupId", groupId);

    @SuppressWarnings("unchecked")
    List<LexData> l = query.getResultList();
    for (LexData lexData : l) {
      retVal.put(lexData.getKeyId().getName(), lexData.getValue());
    }

    return retVal;
  }

}
