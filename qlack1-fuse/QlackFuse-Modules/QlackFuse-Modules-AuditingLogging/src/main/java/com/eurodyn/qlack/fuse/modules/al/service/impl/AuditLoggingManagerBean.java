package com.eurodyn.qlack.fuse.modules.al.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.commons.search.ApplyPagingParams;
import com.eurodyn.qlack.fuse.modules.al.dto.AuditLogDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SearchDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SortDTO;
import com.eurodyn.qlack.fuse.modules.al.enums.AuditLogColumns;
import com.eurodyn.qlack.fuse.modules.al.enums.SearchOperator;
import com.eurodyn.qlack.fuse.modules.al.enums.SortOperator;
import com.eurodyn.qlack.fuse.modules.al.exception.QlackFuseALException;
import com.eurodyn.qlack.fuse.modules.al.exception.QlackFuseALException.CODES;
import com.eurodyn.qlack.fuse.modules.al.model.AlAudit;
import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;
import com.eurodyn.qlack.fuse.modules.al.model.AlAuditTrace;
import com.eurodyn.qlack.fuse.modules.al.service.AuditLoggingManager;
import com.eurodyn.qlack.fuse.modules.al.util.ConverterUtil;
import com.eurodyn.qlack.fuse.modules.al.util.LookupHelper;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Audit logging session bean
 *
 * @author European Dynamics SA
 */
@Stateless(name = "AuditLoggingManagerBean")
public class AuditLoggingManagerBean implements AuditLoggingManager {

  private static final Logger logger = Logger
      .getLogger(AuditLoggingManagerBean.class.getSimpleName());
  @PersistenceContext(unitName = "QlackFuse-AuditLogging-PU")
  private EntityManager em;

  /**
   * {@inheritDoc}
   *
   * @param audit {@inheritDoc}
   * @throws QlackFuseALException {@inheritDoc}
   */
  @Override
  public void logAudit(AuditLogDTO audit) throws QlackFuseALException {
    logger.log(Level.FINER, "Adding audit ''{0}''.", audit);
    AlAudit alAudit = ConverterUtil.convertToAuditLogModel(audit);
    AlAuditLevel level = LookupHelper.getAuditLevelByName(audit.getLevel(), em);
    if (null == level) {
      throw new QlackFuseALException(CODES.ERR_AL_0002, "Level '" +
          audit.getLevel() + "' not found.");
    }
    alAudit.setLevelId(level);
    if (null != alAudit.getTraceId()) {
      em.persist(alAudit.getTraceId());
    }
    em.persist(alAudit);
  }

  private <T> CriteriaQuery<T> addPredicate(CriteriaQuery<T> query,
      CriteriaBuilder cb, Predicate pr) {

    CriteriaQuery<T> cq = query;
    if (cq.getRestriction() != null) {
      cq = cq.where(cb.and(cq.getRestriction(), pr));
    } else {
      cq = cq.where(pr);
    }
    return cq;
  }

  private <T> CriteriaQuery<T> applySearchCriteria(CriteriaBuilder cb,
      CriteriaQuery<T> query,
      Root<AlAudit> root,
      List<String> referenceIds,
      List<String> levelNames,
      List<String> groupNames,
      Date startDate,
      Date endDate) {
    CriteriaQuery<T> cq = query;

    if (referenceIds != null) {
      Predicate pr = root.get("referenceId").in(referenceIds);
      cq = addPredicate(cq, cb, pr);
    }
    if (levelNames != null) {
      Predicate pr = root.get("level").in(levelNames);
      cq = addPredicate(cq, cb, pr);
    }
    if (groupNames != null) {
      Predicate pr = root.get("groupName").in(groupNames);
      cq = addPredicate(cq, cb, pr);
    }

    if (startDate != null) {
      Expression expression = cb.parameter(Date.class, "createdOn");
      cq.where(cb.greaterThanOrEqualTo(expression, startDate));
    } else if (endDate != null) {
      Expression expression = cb.parameter(Date.class, "createdOn");
      cq.where(cb.lessThanOrEqualTo(expression, endDate));
    }

    return cq;
  }

  /**
   * {@inheritDoc}
   *
   * @param id {@inheritDoc}
   */
  @Override
  public void deleteAudit(String id) {
    logger.log(Level.FINER, "Deleting audit ''{0}''.", id);
    em.remove(em.find(AlAudit.class, id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void truncateAudits() {
    logger.log(Level.FINER, "Clearing all audit log data.");
    Query query = em.createQuery("DELETE FROM AlAudit a");
    query.executeUpdate();
  }

  /**
   * {@inheritDoc}
   *
   * @param createdOn {@inheritDoc}
   */
  @Override
  public void truncateAudits(Date createdOn) {
    logger.log(Level.FINER, "Clearing audit log data before {0}", createdOn);
    Query query = em.createQuery("DELETE FROM AlAudit a WHERE a.createdOn < :createdOn");
    query.setParameter("createdOn", createdOn.getTime());
    query.executeUpdate();
  }

  /**
   * {@inheritDoc}
   *
   * @param retentionPeriod {@inheritDoc}
   */
  @Override
  public void truncateAudits(long retentionPeriod) {
    logger.log(Level.FINER, "Clearing audit log data older than {0}",
        String.valueOf(retentionPeriod));
    Query query = em.createQuery("DELETE FROM AlAudit a WHERE a.createdOn < :createdOn");
    query.setParameter("createdOn", Calendar.getInstance().getTimeInMillis() - retentionPeriod);
    query.executeUpdate();
  }

  /**
   * {@inheritDoc}
   *
   * @param levelNames {@inheritDoc}
   * @param referenceIds {@inheritDoc}
   * @param groupNames {@inheritDoc}
   * @param startDate {@inheritDoc}
   * @param endDate {@inheritDoc}
   */
  @Override
  public int countAudits(List<String> levelNames, List<String> referenceIds,
      List<String> groupNames,
      Date startDate, Date endDate) {
    logger.log(Level.FINER,
        "Counting audits, levelNames count = {0}, referenceId count = {1}, groupNames count {2}, "
            + "startDate = {3} and endDate = {4}",
        new String[]{(levelNames != null) ? String.valueOf(levelNames.size()) : "0",
            (referenceIds != null) ? String.valueOf(referenceIds.size()) : "0",
            (groupNames != null) ? String.valueOf(groupNames.size()) : "0",
            (startDate != null) ? startDate.toString() : "NONE",
            (endDate != null) ? endDate.toString() : "NONE"});

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    Root<AlAudit> root = cq.from(AlAudit.class);
    cq = cq.select(cb.count(root));

    cq = applySearchCriteria(cb, cq, root, referenceIds, levelNames, groupNames, startDate,
        endDate);

    TypedQuery<Long> query = em.createQuery(cq);
    return query.getSingleResult().intValue();
  }

  /**
   * {@inheritDoc}
   *
   * @param levelNames {@inheritDoc}
   * @param referenceIds {@inheritDoc}
   * @param groupNames {@inheritDoc}
   * @param startDate {@inheritDoc}
   * @param endDate {@inheritDoc}
   * @param isAscending {@inheritDoc}
   * @param pagingParams {@inheritDoc}
   */
  @Override
  public List<AuditLogDTO> listAudits(List<String> levelNames, List<String> referenceIds,
      List<String> groupNames,
      Date startDate, Date endDate, boolean isAscending, PagingParams pagingParams) {
    logger.log(Level.FINER,
        "Listing audits audits, levelNames count = {0}, referenceId count = {1}, groupNames count {2}, "
            + "startDate = {3} and endDate = {4}",
        new String[]{(levelNames != null) ? String.valueOf(levelNames.size()) : "0",
            (referenceIds != null) ? String.valueOf(referenceIds.size()) : "0",
            (groupNames != null) ? String.valueOf(groupNames.size()) : "0",
            (startDate != null) ? startDate.toString() : "NONE",
            (endDate != null) ? endDate.toString() : "NONE"});

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<AlAudit> cq = cb.createQuery(AlAudit.class);
    Root<AlAudit> root = cq.from(AlAudit.class);

    cq = applySearchCriteria(cb, cq, root, referenceIds, levelNames, groupNames, startDate,
        endDate);

    if (isAscending) {
      cq.orderBy(cb.asc(root.get("createdOn")));
    } else {
      cq.orderBy(cb.desc(root.get("createdOn")));
    }

    TypedQuery<AlAudit> query = em.createQuery(cq);

    List<AlAudit> audits = ApplyPagingParams.apply(query, pagingParams).getResultList();
    return ConverterUtil.convertToAuditLogList(audits);
  }

  @Override
  public List<AuditLogDTO> listAuditLogs(List<SearchDTO> searchList, Date startDate, Date endDate,
      List<SortDTO> sortList, PagingParams pagingParams) {
    logger.log(Level.FINER,
        "listAuditLogs, searchList count = {0}, sortList count = {1}, startDate = {2} and endDate = {3}",
        new String[]{(searchList != null) ? String.valueOf(searchList.size()) : "0",
            (sortList != null) ? String.valueOf(sortList.size()) : "0",
            (startDate != null) ? startDate.toString() : "NONE",
            (endDate != null) ? endDate.toString() : "NONE"});

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<AlAudit> cq = cb.createQuery(AlAudit.class);
    Root<AlAudit> root = cq.from(AlAudit.class);

    cq = applySearchCriteria(cb, cq, root, searchList, startDate, endDate);

    cq = applySortCriteria(cb, cq, root, sortList);

    TypedQuery<AlAudit> query = em.createQuery(cq);

    List<AlAudit> audits = ApplyPagingParams.apply(query, pagingParams).getResultList();
    return ConverterUtil.convertToAuditLogList(audits);
  }

  // new method created due to the fact that
  // field "traceData" is clob
  // and oracle does not support equal and ordering in clob
  // other differences with the old method listAuditLogs
  // 1) supports not equals
  // 2) can filter for traceData, which is a field of a joined table
  // 3) sets upper in sorting for string fields, so that the sorting is case-insensitive
  @Override
  public List<AuditLogDTO> listAuditLogsOracle(List<SearchDTO> searchList, Date startDate,
      Date endDate, List<SortDTO> sortList, PagingParams pagingParams) {
    logger.log(Level.FINER,
        "listAuditLogsOracle, searchList count = {0}, sortList count = {1}, startDate = {2} and endDate = {3}",
        new String[]{(searchList != null) ? String.valueOf(searchList.size()) : "0",
            (sortList != null) ? String.valueOf(sortList.size()) : "0",
            (startDate != null) ? startDate.toString() : "NONE",
            (endDate != null) ? endDate.toString() : "NONE"});

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<AlAudit> cq = cb.createQuery(AlAudit.class);
    Root<AlAudit> root = cq.from(AlAudit.class);

    // fetch objects to minimise queries
    root.fetch("traceId", JoinType.INNER);

    cq = applySearchCriteriaOracle(cb, cq, root, searchList, startDate, endDate);

    cq = applySortCriteriaOracle(cb, cq, root, sortList);

    TypedQuery<AlAudit> query = em.createQuery(cq);

    List<AlAudit> audits = ApplyPagingParams.apply(query, pagingParams).getResultList();
    return ConverterUtil.convertToAuditLogList(audits);
  }

  @Override
  public int countAuditLogs(List<SearchDTO> searchList, Date startDate, Date endDate) {
    logger.log(Level.FINER,
        "countAuditLogs, searchList count = {0}, startDate = {1} and endDate = {2}",
        new String[]{(searchList != null) ? String.valueOf(searchList.size()) : "0",
            (startDate != null) ? startDate.toString() : "NONE",
            (endDate != null) ? endDate.toString() : "NONE"});

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    Root<AlAudit> root = cq.from(AlAudit.class);
    cq = cq.select(cb.count(root));
    cq = applySearchCriteria(cb, cq, root, searchList, startDate, endDate);

    TypedQuery<Long> query = em.createQuery(cq);

    return query.getSingleResult().intValue();
  }

  // new method created due to the fact that
  // field "traceData" is clob
  // and oracle does not support equal in clob
  @Override
  public int countAuditLogsOracle(List<SearchDTO> searchList, Date startDate, Date endDate) {
    logger.log(Level.FINER,
        "countAuditLogsOracle, searchList count = {0}, startDate = {1} and endDate = {2}",
        new String[]{(searchList != null) ? String.valueOf(searchList.size()) : "0",
            (startDate != null) ? startDate.toString() : "NONE",
            (endDate != null) ? endDate.toString() : "NONE"});

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    Root<AlAudit> root = cq.from(AlAudit.class);
    cq = cq.select(cb.count(root));
    cq = applySearchCriteriaOracle(cb, cq, root, searchList, startDate, endDate);

    TypedQuery<Long> query = em.createQuery(cq);

    return query.getSingleResult().intValue();
  }

  private <T> CriteriaQuery<T> applySearchCriteria(CriteriaBuilder cb,
      CriteriaQuery<T> query,
      Root<AlAudit> root,
      List<SearchDTO> searchList,
      Date startDate, Date endDate) {
    CriteriaQuery<T> cq = query;

    if (searchList != null) {
      for (SearchDTO searchDTO : searchList) {
        if (SearchOperator.EQUAL == searchDTO.getOperator()) {
          Predicate pr = root.get(searchDTO.getColumn().name()).in(searchDTO.getValue());
          cq = addPredicate(cq, cb, pr);
        } else if (SearchOperator.LIKE == searchDTO.getOperator()) {
          Predicate pr = cb.like(root.get(searchDTO.getColumn().name()), "%" + searchDTO.getValue().get(0) + "%");

          cq = addPredicate(cq, cb, pr);
        }
      }
    }

    if (startDate != null) {
      Predicate pr = cb.greaterThanOrEqualTo(root.get("createdOn"), startDate.getTime());

      cq = addPredicate(cq, cb, pr);

    }
    if (endDate != null) {
      Predicate pr = cb.lessThanOrEqualTo(root.get("createdOn"), endDate.getTime());

      cq = addPredicate(cq, cb, pr);
    }

    return cq;
  }

  // new method created due to the fact that
  // field "traceData" is clob
  // and oracle does not support equal in clob,
  // the problem can be overcomed using like
  @SuppressWarnings({"rawtypes", "unchecked"})
  private <T> CriteriaQuery<T> applySearchCriteriaOracle(CriteriaBuilder cb,
      CriteriaQuery<T> query,
      Root<AlAudit> root,
      List<SearchDTO> searchList,
      Date startDate, Date endDate) {
    CriteriaQuery<T> cq = query;

    if (searchList != null) {
      for (SearchDTO searchDTO : searchList) {
        if ("traceData".equals(searchDTO.getColumn().name())) {
          Join<AlAudit, AlAuditTrace> rootTrace = root.join("traceId");
          Expression expression = rootTrace.get("traceData");
          // case insensitive search
          expression = cb.upper(expression);
          String fieldValue = searchDTO.getValue().get(0);
          fieldValue = fieldValue.toUpperCase();
          if (SearchOperator.EQUAL == searchDTO.getOperator()) {
            Predicate pr = cb.equal(expression, fieldValue);
            cq = addPredicate(cq, cb, pr);
          } else if (SearchOperator.LIKE == searchDTO.getOperator()) {
            Predicate pr = cb.like(expression, fieldValue);
            cq = addPredicate(cq, cb, pr);
          }
        } else {
          // case insensitive search
          Expression expression = cb.upper(root.get(searchDTO.getColumn().name()));
          String fieldValue = searchDTO.getValue().get(0);
          fieldValue = fieldValue.toUpperCase();
          if (SearchOperator.EQUAL == searchDTO.getOperator()) {
            Predicate pr = cb.equal(expression, fieldValue);
            cq = addPredicate(cq, cb, pr);
          } else if (SearchOperator.LIKE == searchDTO.getOperator()) {
            Predicate pr = cb.like(expression, fieldValue);
            cq = addPredicate(cq, cb, pr);
          } else if (SearchOperator.NOT_EQUAL == searchDTO.getOperator()) {
            Predicate pr = cb.notEqual(expression, fieldValue);
            cq = addPredicate(cq, cb, pr);
          }
        }
      }
    }

    if (startDate != null) {
      Predicate pr = cb.greaterThanOrEqualTo(root.get("createdOn"), startDate.getTime());
      cq = addPredicate(cq, cb, pr);
    }
    if (endDate != null) {
      Predicate pr = cb.lessThanOrEqualTo(root.get("createdOn"), endDate.getTime());
      cq = addPredicate(cq, cb, pr);
    }

    return cq;
  }

  private <T> CriteriaQuery<T> applySortCriteria(CriteriaBuilder cb,
      CriteriaQuery<T> query,
      Root<AlAudit> root,
      List<SortDTO> sortList) {
    CriteriaQuery<T> cq = query;
    List<Order> orders = new ArrayList<>();

    if (sortList != null) {
      for (SortDTO sortDTO : sortList) {
        if (SortOperator.ASC == sortDTO.getOperator()) {
          orders.add(cb.asc(root.get(sortDTO.getColumn().name())));
        } else {
          orders.add(cb.desc(root.get(sortDTO.getColumn().name())));
        }
      }
      cq = cq.orderBy(orders);
    }

    return cq;
  }

  // new method created due to the fact that
  // field "traceData" is clob
  // and oracle does not support order by in clob,
  // the problem can be overcomed by using dbms_lob.substr function
  private <T> CriteriaQuery<T> applySortCriteriaOracle(CriteriaBuilder cb,
      CriteriaQuery<T> query,
      Root<AlAudit> root,
      List<SortDTO> sortList) {
    CriteriaQuery<T> cq = query;
    List<Order> orders = new ArrayList<>();

    if (sortList != null) {
      for (SortDTO sortDTO : sortList) {
        Expression<String> expression;
        if (sortDTO.getColumn().equals(AuditLogColumns.traceData)) {
          Path<String> expressionRoot = root.get("traceId");
          expression = expressionRoot.get("traceData");
        } else {
          expression = root.get(sortDTO.getColumn().name());
        }
        // for all string fields setting upper in sorting, so that the sorting is case-insensitive
        if (!sortDTO.getColumn().equals(AuditLogColumns.createdOn)) {
          expression = cb.upper(expression);
        }
        if (SortOperator.ASC == sortDTO.getOperator()) {
          orders.add(cb.asc(expression));
        } else {
          orders.add(cb.desc(expression));
        }
      }
      cq = cq.orderBy(orders);
    }

    return cq;
  }
}
