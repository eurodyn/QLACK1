package com.eurodyn.qlack.fuse.modules.al.service;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.al.dto.AuditLogDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SearchDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SortDTO;
import com.eurodyn.qlack.fuse.modules.al.exception.QlackFuseALException;
import javax.ejb.Remote;

import java.util.Date;
import java.util.List;

/**
 * To manage audit logging functionality.
 *
 * @author European Dynamics SA
 */
@Remote
public interface AuditLoggingManager {

  /**
   * To log audit. Throws QlackFuseALException in case of unexpected condition.
   *
   * @throws QlackFuseALException If a  level which does not exist has been specified for the audit
   */
  void logAudit(AuditLogDTO audit) throws QlackFuseALException;

  /**
   * To delete specific audit log.
   */
  void deleteAudit(String auditLogId);

  /**
   * Delete all audit logs.
   */
  void truncateAudits();


  /**
   * Delete all audit logs created before a specific date
   *
   * @param createdOn The date before which to clear the audit logs (exclusive).
   */
  void truncateAudits(Date createdOn);


  /**
   * Delete all audit logs older than a specific period of time. The logs which will be deleted are those whose creation
   * date is less than (now - retentionPeriod).
   *
   * @param retentionPeriod The period for which to retain audit logs, in milliseconds
   */
  void truncateAudits(long retentionPeriod);

  /**
   * Returns a list with AuditLogDTO for the given constraints.
   */
  List<AuditLogDTO> listAudits(List<String> levelNames, List<String> referenceIds,
      List<String> groupNames, Date startDate, Date endDate, boolean isAscending,
      PagingParams pagingParams);

  /**
   * Returns the number of entities that the given query will return. Used for pagination.
   */
  int countAudits(List<String> levelNames, List<String> referenceIds,
      List<String> groupNames, Date startDate, Date endDate);

  /**
   * Returns a list of AuditLogDTO's taking account the given search, sort and paging criteria.
   *
   * @param searchList list with search criteria
   * @param startDate from date to search
   * @param endDate to date to search
   * @param sortList list with sort criteria
   * @param pagingParams pagination parameters
   * @return list of AuditLogDTO's
   */
  List<AuditLogDTO> listAuditLogs(List<SearchDTO> searchList, Date startDate, Date endDate,
      List<SortDTO> sortList, PagingParams pagingParams);

  /**
   * Returns a list of AuditLogDTO's taking account the given search, sort and paging criteria. Used for oracle
   * database, since field "traceData" is clob and oracle does not support equal and ordering in clob other differences
   * with the old method listAuditLogs 1) supports not equals 2) can filter for traceData, which is a field of a joined
   * table 3) sets upper in sorting and filtering for string fields, so that the sorting and filtering is
   * case-insensitive 4) for like the entry must have the wildcard %
   *
   * @param searchList list with search criteria
   * @param startDate from date to search
   * @param endDate to date to search
   * @param traceData message to search
   * @param sortList list with sort criteria
   * @param pagingParams pagination parameters
   * @return list of AuditLogDTO's
   */
  List<AuditLogDTO> listAuditLogsOracle(List<SearchDTO> searchList, Date startDate,
      Date endDate, List<SortDTO> sortList, PagingParams pagingParams);

  /**
   * Returns the number of entities that the given query will return. Used for pagination.
   */
  int countAuditLogs(List<SearchDTO> searchList, Date startDate, Date endDate);

  /**
   * Returns the number of entities that the given query will return. Used for pagination. Used for oracle database,
   * since field "traceData" is clob and oracle does not support equal in clob
   */
  int countAuditLogsOracle(List<SearchDTO> searchList, Date startDate, Date endDate);
}
