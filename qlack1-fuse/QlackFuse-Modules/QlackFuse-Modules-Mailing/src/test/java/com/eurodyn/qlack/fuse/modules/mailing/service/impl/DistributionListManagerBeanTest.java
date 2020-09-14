package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.modules.mailing.dto.ContactDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.DistributionListDTO;
import com.eurodyn.qlack.fuse.modules.mailing.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiContact;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiDistributionList;
import com.eurodyn.qlack.fuse.modules.mailing.util.CriteriaBuilderUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import net.bzdyl.ejb3.criteria.Criteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test class for DistributionListManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class DistributionListManagerBeanTest {

  private DistributionListDTO distributionListDTO;
  private MaiDistributionList maiDistributionList;
  private ContactDTO contactDTO;
  private MaiContact maiContact;
  private List<MaiDistributionList> maiDistributionLists;
  @InjectMocks
  private DistributionListManagerBean distributionListManagerBean;
  @Mock
  private EntityManager entityManager;
  @Mock
  private CriteriaBuilderUtil criteriaBuilderUtil;
  @Mock
  private Criteria criteria;
  @Mock
  private Query query;

  @BeforeAll
  void init() {
    InitTestValues initTestValues = new InitTestValues();
    distributionListDTO = initTestValues.createDistributionListDTO();
    maiDistributionList = initTestValues.createMaiDistributionList();
    contactDTO = initTestValues.createContactDTO();
    maiContact = initTestValues.createMaiContact();
    maiDistributionLists = initTestValues.createMaiDistributionLists();
  }

  @Test
  void createDistributionListTest() {
    distributionListManagerBean.createDistributionList(distributionListDTO);
    Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any());
  }

  @Test
  void editDistributionListTest() {
    distributionListManagerBean.editDistributionList(distributionListDTO);
    Mockito.verify(entityManager, Mockito.times(1)).merge(Mockito.any());
  }

  @Test
  void deleteDistributionListTest() {
    Mockito.when(entityManager.find(MaiDistributionList.class, distributionListDTO.getId()))
        .thenReturn(maiDistributionList);
    distributionListManagerBean.deleteDistributionList(distributionListDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).remove(maiDistributionList);
  }

  @Test
  void findTest() {
    distributionListManagerBean.find(distributionListDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).find(MaiDistributionList.class, distributionListDTO.getId());
  }

  @Test
  void addContactToDistributionListTest() {
    Mockito.when(entityManager.find(MaiDistributionList.class, distributionListDTO.getId()))
        .thenReturn(maiDistributionList);
    distributionListManagerBean.addContactToDistributionList(distributionListDTO.getId(), "New Contact Id");
    Mockito.verify(entityManager, Mockito.times(1)).find(MaiDistributionList.class, distributionListDTO.getId());
  }

  @Test
  void createContactTest() {
    distributionListManagerBean.createContact(contactDTO);
    Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any());
  }

  @Test
  void removeContactFromDistributionListTest() {
    Mockito.when(entityManager.find(MaiDistributionList.class, distributionListDTO.getId()))
        .thenReturn(maiDistributionList);
    Mockito.when(entityManager.find(MaiContact.class, maiContact.getId()))
        .thenReturn(maiContact);
    distributionListManagerBean.removeContactFromDistributionList(distributionListDTO.getId(), maiContact.getId());
    Mockito.verify(entityManager, Mockito.times(1)).find(MaiDistributionList.class, distributionListDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).find(MaiContact.class, maiContact.getId());
  }

  @Test
  void searchEmptyTest() throws NoSuchFieldException {
    mockCriteriaBuilderUtil();
    Assertions.assertEquals(new ArrayList<>(), distributionListManagerBean.search(distributionListDTO));
  }

  @Test
  void searchNullTest() throws NoSuchFieldException {
    mockCriteriaBuilderUtil();
    Assertions.assertEquals(new ArrayList<>(), distributionListManagerBean.search(null));
  }

  @Test
  void searchEmptyNameTest() throws NoSuchFieldException {
    distributionListDTO.setName(null);
    mockCriteriaBuilderUtil();
    Assertions.assertEquals(new ArrayList<>(), distributionListManagerBean.search(distributionListDTO));
  }

  @Test
  void searchTest() throws NoSuchFieldException {
    mockCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiDistributionLists);
    Assertions.assertEquals(1, distributionListManagerBean.search(distributionListDTO).size());
  }

  private void mockCriteriaBuilderUtil() throws NoSuchFieldException {
    FieldSetter.setField(distributionListManagerBean,
        distributionListManagerBean.getClass().getDeclaredField("criteriaBuilderUtil"),
        criteriaBuilderUtil);
    Mockito.when(criteriaBuilderUtil.getCriteria()).thenReturn(criteria);
    Mockito.when(criteria.prepareQuery(entityManager)).thenReturn(query);
  }

}
