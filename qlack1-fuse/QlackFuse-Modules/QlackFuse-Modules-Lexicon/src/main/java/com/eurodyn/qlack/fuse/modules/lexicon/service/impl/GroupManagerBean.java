package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.commons.search.ApplyPagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexGroupDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException.CODES;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexGroup;
import com.eurodyn.qlack.fuse.modules.lexicon.service.GroupManager;
import com.eurodyn.qlack.fuse.modules.lexicon.util.ConverterUtil;
import com.eurodyn.qlack.fuse.modules.lexicon.util.LexiconValidationUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Stateless Session EJB and also web service implementation class providing methods for group management like CRUD
 * operation for Group and Lexicon Group Mapping table.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Stateless(name = "GroupManagerBean")
public class GroupManagerBean implements GroupManager {

  private static final Logger logger = Logger.getLogger(GroupManagerBean.class.getName());
  @PersistenceContext(unitName = "QlackFuse-Lexicon-PU")
  private EntityManager em;

  /**
   * {@inheritDoc}
   *
   * @param lexGroupDTO {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexGroupDTO createGroup(LexGroupDTO lexGroupDTO) {
    LexGroup group = ConverterUtil.lexGroupDTOtoLexGroup(lexGroupDTO);
    group.setCreatedOn(System.currentTimeMillis());
    em.persist(group);
    lexGroupDTO.setId(group.getId());

    return lexGroupDTO;
  }

  /**
   * {@inheritDoc}
   *
   * @param groupID {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void deleteGroupByID(String groupID) throws QlackFuseLexiconException {
    LexiconValidationUtil.validateNullForGroupID(groupID);
    try {
      LexGroup group = em.find(LexGroup.class, groupID);
      em.remove(group);
    } catch (IllegalArgumentException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0005,
          "Group does not exist with the provided groupId.");
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param groupID {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexGroupDTO viewGroupByID(String groupID) throws QlackFuseLexiconException {
    LexiconValidationUtil.validateNullForGroupID(groupID);
    LexGroup group = em.find(LexGroup.class, groupID);
    if (group == null) {
      return null;
    } else {
      return ConverterUtil.lexGroupToLexGroupDTO(group);
    }
  }


  /**
   * {@inheritDoc}
   *
   * @param lexGroupDTO {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexGroupDTO updateGroup(LexGroupDTO lexGroupDTO) throws QlackFuseLexiconException {
    LexGroup group = ConverterUtil.lexGroupDTOtoLexGroup(lexGroupDTO);
    group.setLastModifiedOn(System.currentTimeMillis());
    em.merge(group);

    return viewGroupByID(group.getId());
  }

  /**
   * {@inheritDoc}
   *
   * @param paging {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public LexGroupDTO[] listGroups(PagingParams paging) {
    Query query = em.createQuery("select g from LexGroup g order by g.title");
    query = ApplyPagingParams.apply(query, paging);
    List<LexGroupDTO> retVal = new ArrayList<>();
    for (LexGroup o : (Iterable<LexGroup>) query.getResultList()) {
      retVal.add(ConverterUtil.lexGroupToLexGroupDTO(o));
    }

    return retVal.toArray(new LexGroupDTO[retVal.size()]);
  }

  /**
   * {@inheritDoc}
   *
   * @param searchTerm {@inheritDoc}
   * @param paging {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexGroupDTO[] searchGroups(String searchTerm, PagingParams paging)
      throws QlackFuseLexiconException {
    LexiconValidationUtil.validateNullForSearchTerm(searchTerm);

    StringBuilder quString = new StringBuilder("select grp from LexGroup grp ");
    quString.append("where upper(grp.description) like :descSearchTerm ");
    quString.append("or UPPER(grp.title) like :titleSearchTerm ");
    Query query = em.createQuery(quString.toString());
    query.setParameter("descSearchTerm", "%" + searchTerm.toUpperCase() + "%");
    query.setParameter("titleSearchTerm", "%" + searchTerm.toUpperCase() + "%");
    query = ApplyPagingParams.apply(query, paging);
    List<LexGroupDTO> retVal = new ArrayList<>();
    for (LexGroup o : (Iterable<LexGroup>) query.getResultList()) {
      retVal.add(ConverterUtil.lexGroupToLexGroupDTO(o));
    }

    return retVal.toArray(new LexGroupDTO[0]);
  }

}
