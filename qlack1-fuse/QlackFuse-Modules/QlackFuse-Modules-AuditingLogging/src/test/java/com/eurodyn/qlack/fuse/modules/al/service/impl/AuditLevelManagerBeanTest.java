package com.eurodyn.qlack.fuse.modules.al.service.impl;

import com.eurodyn.qlack.fuse.modules.al.dto.AuditLevelDTO;
import com.eurodyn.qlack.fuse.modules.al.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

import java.util.List;

/**
 * Unit test class for AuditLevelManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class AuditLevelManagerBeanTest {

  @InjectMocks
  private AuditLevelManagerBean auditLevelManagerBean;

  @Mock
  private EntityManager entityManager;

  @Mock
  private Query query;

  private AuditLevelDTO auditLevelDTO;

  private AlAuditLevel alAuditLevel;

  private List<AlAuditLevel> alAuditLevels;

  @BeforeAll
  void init() {
    InitTestValues initTestValues = new InitTestValues();
    auditLevelDTO = initTestValues.createAuditLevelDTO();
    alAuditLevel = initTestValues.createAlAuditLevel();
    alAuditLevels = initTestValues.createAlAuditLevels();
  }

  @Test
  void addLevelTest() {
    auditLevelManagerBean.addLevel(auditLevelDTO);
    Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any());
  }

  @Test
  void deleteLevelById() {
    Mockito.when(entityManager.find(AlAuditLevel.class, auditLevelDTO.getId())).thenReturn(alAuditLevel);
    auditLevelManagerBean.deleteLevelById(auditLevelDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).remove(alAuditLevel);
  }

  @Test
  void updateLevelTest() {
    auditLevelManagerBean.updateLevel(auditLevelDTO);
    Mockito.verify(entityManager, Mockito.times(1)).merge(Mockito.any());
  }

  @Test
  void listAuditLevelsTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(alAuditLevels);
    Assertions.assertEquals(1, auditLevelManagerBean.getAllAudLevelsFromDB().size());
  }

}
