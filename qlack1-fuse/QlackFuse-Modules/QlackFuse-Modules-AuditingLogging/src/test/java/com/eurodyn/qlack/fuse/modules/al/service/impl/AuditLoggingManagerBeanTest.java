package com.eurodyn.qlack.fuse.modules.al.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.al.dto.SearchDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SortDTO;
import com.eurodyn.qlack.fuse.modules.al.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.al.model.AlAudit;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Unit test class for AuditLoggingManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class AuditLoggingManagerBeanTest {

  @InjectMocks
  private AuditLoggingManagerBean auditLoggingManagerBean;

  @Mock
  private EntityManager entityManager;

  @Mock
  private Query query;

  @Mock
  private CriteriaBuilder criteriaBuilder;

  @Mock
  private CriteriaQuery criteriaQuery;

  @Mock
  private Root<AlAudit> root;

  @Mock
  private Path<Object> path;

  @Mock
  private Predicate predicate;

  @Mock
  private TypedQuery<Long> typedQuery;

  @Mock
  private PagingParams pagingParams;

  @Mock
  private TypedQuery<AlAudit> typedQueryAudit;

  @Mock
  private Expression expression;

  private AlAudit alAudit;

  private List<AlAudit> alAudits;

  private List<SearchDTO> searchDTOS;

  private List<SearchDTO> likeSearchDTOS;

  private List<SortDTO> sortDTOS;

  private List<SortDTO> reverseSortDTOS;

  @BeforeAll
  void init() {
    InitTestValues initTestValues = new InitTestValues();
    alAudit = initTestValues.createAlAudit();
    alAudits = initTestValues.createAlAudits();
    searchDTOS = initTestValues.createSearchDTOs();
    sortDTOS = initTestValues.createSortDTOs();
    reverseSortDTOS = initTestValues.createReverseSortDTOs();
    likeSearchDTOS = initTestValues.createLikeSearchDTOs();
  }

  @Test
  void deleteAuditTest() {
    Mockito.when(entityManager.find(AlAudit.class, alAudit.getId())).thenReturn(alAudit);
    auditLoggingManagerBean.deleteAudit(alAudit.getId());
    Mockito.verify(entityManager, Mockito.times(1)).remove(alAudit);
  }

  @Test
  void truncateAuditsTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    auditLoggingManagerBean.truncateAudits();
    Mockito.verify(query, Mockito.times(1)).executeUpdate();
  }

  @Test
  void truncateAuditsDatesTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    auditLoggingManagerBean.truncateAudits(new Date());
    Mockito.verify(query, Mockito.times(1)).executeUpdate();
  }

  @Test
  void truncateAuditsRetentionTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    auditLoggingManagerBean.truncateAudits(new Date().getTime());
    Mockito.verify(query, Mockito.times(1)).executeUpdate();
  }

  @Test
  void countAuditsNullTest() {
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(Long.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.select(criteriaBuilder.count(root))).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    Mockito.when(typedQuery.getSingleResult()).thenReturn(0L);
    Assertions.assertEquals(0, auditLoggingManagerBean.countAudits(null, null, null, null, null));
  }

  @Test
  void countAuditsTest() {
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(Long.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.select(criteriaBuilder.count(root))).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(path.in(Mockito.anyCollection())).thenReturn(predicate);
    Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    Mockito.when(typedQuery.getSingleResult()).thenReturn(1L);
    Assertions.assertEquals(1, auditLoggingManagerBean.countAudits(Arrays.asList("Test Level"), Arrays.asList("Test "
        + "Reference"), Arrays.asList("Test Group"), new Date(), null));
  }

  @Test
  void countAuditsScenario2Test() {
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(Long.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.select(criteriaBuilder.count(root))).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(path.in(Mockito.anyCollection())).thenReturn(predicate);
    Mockito.when(criteriaQuery.where(criteriaBuilder.and(criteriaQuery.getRestriction(), predicate)))
        .thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.getRestriction()).thenReturn(predicate);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    Mockito.when(typedQuery.getSingleResult()).thenReturn(1L);
    Assertions.assertEquals(1, auditLoggingManagerBean.countAudits(Arrays.asList("Test Level"), null, null, null,
        new Date()));
  }

  @Test
  void listAuditsNullTest() {
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(AlAudit.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQueryAudit);
    Assertions.assertEquals(0, auditLoggingManagerBean.listAudits(null, null, null,
        null, null, false, pagingParams).size());
  }

  @Test
  void listAuditsTest() {
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(AlAudit.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
    Mockito.when(path.in(Mockito.anyCollection())).thenReturn(predicate);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQueryAudit);
    Mockito.when(typedQueryAudit.getResultList()).thenReturn(alAudits);
    Assertions.assertEquals(1, auditLoggingManagerBean.listAudits(Arrays.asList("Test Level"), Arrays.asList("Test "
            + "Reference"), Arrays.asList("Test Group"),
        new Date(), new Date(), true, pagingParams).size());
  }

  @Test
  void listAuditLogsNullTest() {
    Date now = new Date();
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(AlAudit.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQueryAudit);
    Assertions.assertEquals(0, auditLoggingManagerBean.listAuditLogs(null,
        null, null, null, pagingParams).size());
  }

  @Test
  void listAuditLogsTest() {
    Date now = new Date();
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(AlAudit.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(path.in(Mockito.anyCollection())).thenReturn(predicate);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(criteriaBuilder.greaterThanOrEqualTo(root.get("createOn"), now.getTime())).thenReturn(predicate);
    Mockito.when(criteriaQuery.orderBy(Mockito.anyList())).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQueryAudit);
    Mockito.when(typedQueryAudit.getResultList()).thenReturn(alAudits);
    Assertions.assertEquals(1, auditLoggingManagerBean.listAuditLogs(searchDTOS,
        now, null, sortDTOS, pagingParams).size());
  }

  @Test
  void listAuditLogsScenario2Test() {
    Date now = new Date();
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(AlAudit.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(path.in(Mockito.anyCollection())).thenReturn(predicate);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(criteriaBuilder.lessThanOrEqualTo(root.get("createOn"), now.getTime())).thenReturn(predicate);
    Mockito.when(criteriaQuery.orderBy(Mockito.anyList())).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQueryAudit);
    Mockito.when(typedQueryAudit.getResultList()).thenReturn(alAudits);
    Assertions.assertEquals(1, auditLoggingManagerBean.listAuditLogs(searchDTOS,
        null, now, reverseSortDTOS, pagingParams).size());
  }

  @Test
  void listAuditLogsOracleNullTest() {
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(AlAudit.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQueryAudit);
    Assertions.assertEquals(0, auditLoggingManagerBean.listAuditLogsOracle(null,
        null, null, null, pagingParams).size());
  }

  @Test
  void listAuditLogsOracleTest() {
    Date now = new Date();
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(AlAudit.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(criteriaBuilder.upper(root.get(Mockito.anyString()))).thenReturn(expression);
    Mockito.when(criteriaBuilder.equal(expression, searchDTOS.get(0).getValue().get(0).toUpperCase()))
        .thenReturn(predicate);
    Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
    Mockito.when(criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"), now.getTime())).thenReturn(predicate);
    Mockito.when(criteriaQuery.orderBy(Mockito.anyList())).thenReturn(criteriaQuery);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQueryAudit);
    Mockito.when(typedQueryAudit.getResultList()).thenReturn(alAudits);
    Assertions.assertEquals(1, auditLoggingManagerBean.listAuditLogsOracle(searchDTOS,
        now, null, reverseSortDTOS, pagingParams).size());
  }

  @Test
  void listAuditLogsOracleScenario2Test() {
    Date now = new Date();
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(AlAudit.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(criteriaBuilder.upper(root.get(Mockito.anyString()))).thenReturn(expression);
    Mockito.when(criteriaBuilder.equal(expression, searchDTOS.get(0).getValue().get(0).toUpperCase()))
        .thenReturn(predicate);
    Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
    Mockito.when(criteriaBuilder.lessThanOrEqualTo(root.get("createdOn"), now.getTime())).thenReturn(predicate);
    Mockito.when(criteriaQuery.orderBy(Mockito.anyList())).thenReturn(criteriaQuery);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQueryAudit);
    Mockito.when(typedQueryAudit.getResultList()).thenReturn(alAudits);
    Assertions.assertEquals(1, auditLoggingManagerBean.listAuditLogsOracle(searchDTOS,
        null, now, reverseSortDTOS, pagingParams).size());
  }

  @Test
  void countAuditLogsNullTest() {
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(Long.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(criteriaQuery.select(criteriaBuilder.count(root))).thenReturn(criteriaQuery);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    Mockito.when(typedQuery.getSingleResult()).thenReturn(0L);
    Assertions.assertEquals(0, auditLoggingManagerBean.countAuditLogs(null, null, null));
  }

  @Test
  void countAuditLogsTest() {
    Date now = new Date();
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(Long.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(criteriaQuery.select(criteriaBuilder.count(root))).thenReturn(criteriaQuery);
    Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
    Mockito.when(path.in(Mockito.anyCollection())).thenReturn(predicate);
    Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
    Mockito.when(criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"), now.getTime())).thenReturn(predicate);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    Mockito.when(typedQuery.getSingleResult()).thenReturn(1L);
    Assertions.assertEquals(1, auditLoggingManagerBean.countAuditLogs(searchDTOS, now, null));
  }

  @Test
  void countAuditLogsOracleNullTest() {
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(Long.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(criteriaQuery.select(criteriaBuilder.count(root))).thenReturn(criteriaQuery);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    Mockito.when(typedQuery.getSingleResult()).thenReturn(0L);
    Assertions.assertEquals(0, auditLoggingManagerBean.countAuditLogsOracle(null, null, null));
  }

  @Test
  void countAuditLogsOracleTest() {
    Date now = new Date();
    Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    Mockito.when(criteriaBuilder.createQuery(Long.class)).thenReturn(criteriaQuery);
    Mockito.when(criteriaQuery.from(AlAudit.class)).thenReturn(root);
    Mockito.when(criteriaQuery.select(criteriaBuilder.count(root))).thenReturn(criteriaQuery);
    Mockito.when(criteriaBuilder.upper(root.get(Mockito.anyString()))).thenReturn(expression);
    Mockito.when(criteriaBuilder.like(expression, likeSearchDTOS.get(0).getValue().get(0).toUpperCase()))
        .thenReturn(predicate);
    Mockito.when(criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"), now.getTime())).thenReturn(predicate);
    Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
    Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    Mockito.when(typedQuery.getSingleResult()).thenReturn(1L);
    Assertions.assertEquals(1, auditLoggingManagerBean.countAuditLogsOracle(likeSearchDTOS, now, null));
  }


}
