package com.eurodyn.qlack.fuse.modules.al.util;

import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;
import com.eurodyn.qlack.fuse.modules.al.service.AlAuditSingleton;

import javax.persistence.EntityManager;

import java.util.logging.Logger;

/**
 * Implementation for LookupHelper
 *
 * @author European Dynamics SA
 */
public class LookupHelper {

  private static final Logger logger = Logger.getLogger(LookupHelper.class.getName());

  private LookupHelper() {
  }

  /**
   * Retrieve an audit level by its name
   *
   * @param levelName The name of the level to retrieve
   * @return The audit level entity or null if a level with this name does not exist
   */
  public static AlAuditLevel getAuditLevelByName(String levelName, EntityManager em) {
    logger.info("Retrieving audit level with name " + levelName);
    return AlAuditSingleton.getInstance().getAuditLevelByName(levelName);
  }
}
