package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import com.eurodyn.qlack.fuse.modules.lexicon.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexLanguage;
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

import java.util.Arrays;
import java.util.HashMap;

/**
 * Unit test class for LanguageManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
class LanguageManagerBeanTest {

  private static final String EN_UK = "en_uk";
  private static final String EN = "en";
  private static LexLanguageDTO lexLanguageDTO;
  private static LexLanguage lexLanguage;
  private static LexLanguage[] lexLanguages;
  private static HashMap<String, String> hashMap;
  @InjectMocks
  private LanguageManagerBean languageManagerBean;
  @Mock
  private EntityManager entityManager;
  @Mock
  private KeyManagerBean keyManagerBean;
  @Mock
  private Query query;
  @Mock
  private PagingParams pagingParams;

  @BeforeAll
  static void init() {
    InitTestValues initTestValues = new InitTestValues();
    lexLanguageDTO = initTestValues.createLexLanguageDTO();
    lexLanguage = initTestValues.createLexLanguage();
    lexLanguages = initTestValues.createLexLanguages();
    hashMap = initTestValues.createLanguageHashMap();
  }

  @Test
  void createLanguageTest() {
    Assertions.assertEquals(lexLanguageDTO.getName(), languageManagerBean.createLanguage(lexLanguageDTO).getName());
  }

  @Test
  void viewLanguageNullTest() throws QlackFuseLexiconException {
    Assertions.assertEquals(null, languageManagerBean.viewLanguage(lexLanguageDTO.getId()));
  }

  @Test
  void viewLanguageTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexLanguage.class, lexLanguageDTO.getId())).thenReturn(lexLanguage);
    Assertions.assertEquals(lexLanguage.getName(), languageManagerBean.viewLanguage(lexLanguageDTO.getId()).getName());
  }

  @Test
  void getLanguageByLocaleTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getSingleResult()).thenReturn(lexLanguage);
    Assertions.assertEquals(lexLanguage.getName(),
        languageManagerBean.getLanguageByLocale(lexLanguageDTO.getLocale()).getName());
  }

  @Test
  void updateLanguageTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexLanguage.class, lexLanguageDTO.getId())).thenReturn(lexLanguage);
    Assertions.assertEquals(lexLanguage.getName(), languageManagerBean.updateLanguage(lexLanguageDTO).getName());
  }

  @Test
  void listLanguagesEmptyTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, languageManagerBean.listLanguages(pagingParams, true).length);
  }

  @Test
  void listLanguagesEmptyOnlyActiveTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, languageManagerBean.listLanguages(pagingParams, false).length);
  }

  @Test
  void listLanguagesTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(Arrays.asList(lexLanguages));
    Assertions.assertEquals(1, languageManagerBean.listLanguages(pagingParams, true).length);
  }

  @Test
  void getEffectiveLanguageNullTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(EN, languageManagerBean.getEffectiveLanguage(EN_UK, EN));
  }

  @Test
  void getEffectiveLanguageTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(Arrays.asList(lexLanguages));
    Assertions.assertEquals(EN_UK, languageManagerBean.getEffectiveLanguage(EN_UK, EN));
  }

  @Test
  void getEffectiveLanguageScenario2Test() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(Arrays.asList(lexLanguages));
    Assertions.assertEquals("en", languageManagerBean.getEffectiveLanguage(null, EN));
  }

  @Test
  void downloadLanguageNullTest() throws QlackFuseLexiconException {
    Assertions.assertNotNull(languageManagerBean.downloadLanguage(EN));
  }

  @Test
  void downloadLanguageTest() throws QlackFuseLexiconException {
    Mockito.when(keyManagerBean.getTranslationsForLocale(EN, null)).thenReturn(hashMap);
    Assertions.assertNotNull(languageManagerBean.downloadLanguage(EN));
  }

}
