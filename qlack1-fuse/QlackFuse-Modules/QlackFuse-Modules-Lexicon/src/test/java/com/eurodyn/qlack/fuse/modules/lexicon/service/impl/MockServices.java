package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class MockServices {

  private LanguageManagerBean languageManagerBean;
  private EntityManager entityManager;
  private EntityTransaction entityTransaction;

  public MockServices() {
    languageManagerBean = mock(LanguageManagerBean.class);
    entityManager = mock(EntityManager.class);
    entityTransaction = mock(EntityTransaction.class);
    when(entityManager.getTransaction()).thenReturn(entityTransaction);
  }

  public LanguageManagerBean getLanguageManagerBean() {
    return languageManagerBean;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }
}
