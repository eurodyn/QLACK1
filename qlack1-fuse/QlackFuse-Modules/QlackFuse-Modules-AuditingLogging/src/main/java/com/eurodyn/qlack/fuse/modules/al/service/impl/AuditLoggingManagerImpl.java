package com.eurodyn.qlack.fuse.modules.al.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.al.dto.AuditLogDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SearchDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SortDTO;
import com.eurodyn.qlack.fuse.modules.al.service.AuditLoggingManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

@Stateless(name = "AuditLoggingManagerImpl")
public class AuditLoggingManagerImpl implements AuditLoggingManager {

  @PersistenceContext(unitName = "QlackFuse-AuditLogging-PU")
  private EntityManager entityManager;

  @Override
  public void logAudit(AuditLogDTO audit) {

  }

  @Override
  public void deleteAudit(String auditLogId) {

  }

  @Override
  public void truncateAudits() {

  }

  @Override
  public void truncateAudits(Date createdOn) {

  }

  @Override
  public void truncateAudits(long retentionPeriod) {

  }

  @Override
  public List<AuditLogDTO> listAudits(List<String> levelNames, List<String> referenceIds, List<String> groupNames,
      Date startDate, Date endDate, boolean isAscending, PagingParams pagingParams) {
    return null;
  }

  @Override
  public int countAudits(List<String> levelNames, List<String> referenceIds, List<String> groupNames, Date startDate,
      Date endDate) {
    return 0;
  }

  @Override
  public List<AuditLogDTO> listAuditLogs(List<SearchDTO> searchList, Date startDate, Date endDate,
      List<SortDTO> sortList, PagingParams pagingParams) {
    return null;
  }

  @Override
  public List<AuditLogDTO> listAuditLogsOracle(List<SearchDTO> searchList, Date startDate, Date endDate,
      List<SortDTO> sortList, PagingParams pagingParams) {
    return null;
  }

  @Override
  public int countAuditLogs(List<SearchDTO> searchList, Date startDate, Date endDate) {
    return 0;
  }

  @Override
  public int countAuditLogsOracle(List<SearchDTO> searchList, Date startDate, Date endDate) {
    return 0;
  }
}
