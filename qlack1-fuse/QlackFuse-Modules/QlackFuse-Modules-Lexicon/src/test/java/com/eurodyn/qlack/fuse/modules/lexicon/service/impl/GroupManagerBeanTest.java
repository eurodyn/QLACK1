package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexGroupDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import com.eurodyn.qlack.fuse.modules.lexicon.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexGroup;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * Unit test class for GroupManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
class GroupManagerBeanTest {

  private static LexGroupDTO lexGroupDTO;
  private static LexGroup lexGroup;
  private static List<LexGroup> lexGroups;
  @InjectMocks
  private GroupManagerBean groupManagerBean;
  @Mock
  private EntityManager entityManager;
  @Mock
  private PagingParams pagingParams;
  @Mock
  private Query query;

  @BeforeAll
  static void init() {
    InitTestValues initTestValues = new InitTestValues();
    lexGroupDTO = initTestValues.createLexGroupDTO();
    lexGroup = initTestValues.createLexGroup();
    lexGroups = initTestValues.createLexGroups();
  }

  @Test
  void createGroupTest() {
    Assertions.assertEquals(groupManagerBean.createGroup(lexGroupDTO), lexGroupDTO);
  }

  @Test
  void deleteGroupByIdTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexGroup.class, lexGroupDTO.getId())).thenReturn(lexGroup);
    groupManagerBean.deleteGroupByID(lexGroupDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).remove(lexGroup);
  }

  @Test
  public void deleteGroupByIdNotFoundTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexGroup.class, lexGroupDTO.getId())).thenThrow(new IllegalArgumentException());
    Assertions
        .assertThrows(QlackFuseLexiconException.class, () -> groupManagerBean.deleteGroupByID(lexGroupDTO.getId()));
  }

  @Test
  void viewGroupByIDNullTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexGroup.class, lexGroupDTO.getId())).thenReturn(null);
    Assertions.assertEquals(null, groupManagerBean.viewGroupByID(lexGroupDTO.getId()));
  }

  @Test
  void viewGroupByIDTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexGroup.class, lexGroupDTO.getId())).thenReturn(lexGroup);
    Assertions.assertEquals(lexGroupDTO.getTitle(), groupManagerBean.viewGroupByID(lexGroupDTO.getId()).getTitle());
  }

  @Test
  void updateGroupTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexGroup.class, lexGroupDTO.getId())).thenReturn(lexGroup);
    Assertions.assertEquals(lexGroupDTO.getTitle(), groupManagerBean.updateGroup(lexGroupDTO).getTitle());
  }

  @Test
  void listGroupsEmptyTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, groupManagerBean.listGroups(pagingParams).length);
  }

  @Test
  void listGroupsTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(lexGroups);
    Assertions.assertEquals(1, groupManagerBean.listGroups(pagingParams).length);
  }

  @Test
  void searchGroupsEmptyTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, groupManagerBean.searchGroups("search term", pagingParams).length);
  }

  @Test
  void searchGroupsTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(lexGroups);
    Assertions.assertEquals(1, groupManagerBean.searchGroups("search term", pagingParams).length);
  }

}
