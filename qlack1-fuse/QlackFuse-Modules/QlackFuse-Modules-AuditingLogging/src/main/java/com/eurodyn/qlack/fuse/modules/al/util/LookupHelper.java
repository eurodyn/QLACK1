package com.eurodyn.qlack.fuse.modules.al.util;

import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Implementation for LookupHelper
 *
 * @author European Dynamics SA
 */
@Slf4j
public class LookupHelper {

  /**
   * Retrieve an audit level by its name
   *
   * @param levelName The name of the level to retrieve
   * @return The audit level entity or null if a level with this name does not exist
   */
  public static AlAuditLevel getAuditLevelByName(String levelName, EntityManager em) {
    log.info("Retrieving audit level with name {0}", levelName);

    Query q = em.createQuery("SELECT l FROM AlAuditLevel l WHERE l.name = :name");
    q.setParameter("name", levelName);
    List<AlAuditLevel> resultList = q.getResultList();
    if ((resultList != null) && (!resultList.isEmpty())) {
      return resultList.get(0);
    } else {
      return null;
    }
  }
}