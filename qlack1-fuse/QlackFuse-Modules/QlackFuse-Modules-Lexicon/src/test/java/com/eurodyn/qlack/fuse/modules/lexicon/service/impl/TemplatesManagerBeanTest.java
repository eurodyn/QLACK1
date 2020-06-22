package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import com.eurodyn.qlack.commons.exception.QlackCommonsException;
import com.eurodyn.qlack.fuse.modules.lexicon.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexTemplate;
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
 * Unit test class for TemplatesManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
class TemplatesManagerBeanTest {

  private static final String TEMPLATE_NAME = "Test Template";
  private static List<LexTemplate> lexTemplates;
  @InjectMocks
  private TemplatesManagerBean templatesManagerBean;
  @Mock
  private EntityManager entityManager;
  @Mock
  private Query query;

  @BeforeAll
  static void init() {
    InitTestValues initTestValues = new InitTestValues();
    lexTemplates = initTestValues.createLexTemplates();
  }

  @Test
  void getTemplateByNameEmptyTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(null, templatesManagerBean.getTemplateByName(TEMPLATE_NAME));
  }

  @Test
  void getTemplateByNameTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(lexTemplates);
    Assertions.assertEquals(lexTemplates.get(0).getValue(), templatesManagerBean.getTemplateByName(TEMPLATE_NAME));
  }

  @Test
  void processTemplateByNameEmptyTest() throws QlackCommonsException {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertNull(templatesManagerBean.processTemplateByName(TEMPLATE_NAME, null));
  }

  @Test
  void processTemplateByNameTest() throws QlackCommonsException {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(lexTemplates);
    Assertions.assertEquals(TEMPLATE_NAME, templatesManagerBean.processTemplateByName(TEMPLATE_NAME, null));
  }

}
