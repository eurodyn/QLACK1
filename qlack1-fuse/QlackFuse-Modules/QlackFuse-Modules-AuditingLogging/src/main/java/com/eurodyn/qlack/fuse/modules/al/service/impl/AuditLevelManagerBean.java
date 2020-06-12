package com.eurodyn.qlack.fuse.modules.al.service.impl;

import com.eurodyn.qlack.fuse.modules.al.dto.AuditLevelDTO;
import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;
import com.eurodyn.qlack.fuse.modules.al.service.AuditLevelManager;
import com.eurodyn.qlack.fuse.modules.al.util.ConverterUtil;
import com.eurodyn.qlack.fuse.modules.al.util.LookupHelper;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entity bean implementation of AuditLevelRemote
 *
 * @author European Dynamics SA
 */
@Stateless(name = "AuditLevelManagerBean")
public class AuditLevelManagerBean implements AuditLevelManager {

  private static final Logger logger = Logger
      .getLogger(AuditLevelManagerBean.class.getSimpleName());
  @PersistenceContext(unitName = "QlackFuse-AuditLogging-PU")
  private EntityManager em;

  /**
   * {@inheritDoc}
   *
   * @param level {@inheritDoc}
   */
  @Override
  public String addLevel(AuditLevelDTO level) {
    logger.log(Level.FINER, "Adding custom Audit level ''{0}''.", level);
    AlAuditLevel alLevel = ConverterUtil.convertToAuditLevelModel(level);
    alLevel.setCreatedOn(System.currentTimeMillis());
    em.persist(alLevel);
    return alLevel.getId();
  }

  /**
   * {@inheritDoc}
   *
   * @param levelId {@inheritDoc}
   */
  @Override
  public void deleteLevelById(String levelId) {
    logger.log(Level.FINER, "Deleting Audit level with id ''{0}''.", levelId);
    em.remove(em.find(AlAuditLevel.class, levelId));
  }

  /**
   * {@inheritDoc}
   *
   * @param levelName {@inheritDoc}
   */
  @Override
  public void deleteLevelByName(String levelName) {
    logger.log(Level.FINER, "Deleting Audit level with name ''{0}''.", levelName);
    em.remove(LookupHelper.getAuditLevelByName(levelName, em));
  }

  /**
   * {@inheritDoc}
   *
   * @param level {@inheritDoc}
   */
  @Override
  public void updateLevel(AuditLevelDTO level) {
    logger.log(Level.FINER, "Updating Audit level ''{0}'',", level);
    AlAuditLevel lev = ConverterUtil.convertToAuditLevelModel(level);
    em.merge(lev);
  }

  /**
   * {@inheritDoc}
   *
   * @param levelName {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public AuditLevelDTO getAuditLevelByName(String levelName) {
    logger.log(Level.FINER, "Searching Audit level by name ''{0}''.", levelName);
    AlAuditLevel level = LookupHelper.getAuditLevelByName(levelName, em);
    return ConverterUtil.convertToAuditLevelDTO(level);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public List<AuditLevelDTO> listAuditLevels() {
    logger.log(Level.FINER, "Retrieving all audit levels");
    Query q = em.createQuery("SELECT l FROM AlAuditLevel l");
    return ConverterUtil.convertToAuditLevelList(q.getResultList());
  }
}
