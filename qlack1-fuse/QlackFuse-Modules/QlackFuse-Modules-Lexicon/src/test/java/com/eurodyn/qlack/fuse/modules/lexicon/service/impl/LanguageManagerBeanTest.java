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
public class LanguageManagerBeanTest {

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

  private static InitTestValues initTestValues;

  private static LexLanguageDTO lexLanguageDTO;

  private static LexLanguage lexLanguage;

  private static LexLanguage[] lexLanguages;

  private final static String EN_UK = "en_uk";

  private final static String EN = "en";

  private static HashMap<String, String> hashMap;

  @BeforeAll
  public static void init() {
    initTestValues = new InitTestValues();
    lexLanguageDTO = initTestValues.createLexLanguageDTO();
    lexLanguage = initTestValues.createLexLanguage();
    lexLanguages = initTestValues.createLexLanguages();
    hashMap = initTestValues.createLanguageHashMap();
  }

  @Test
  public void createLanguageTest() {
    Assertions.assertEquals(lexLanguageDTO.getName(), languageManagerBean.createLanguage(lexLanguageDTO).getName());
  }

  @Test
  public void viewLanguageNullTest() throws QlackFuseLexiconException {
    Assertions.assertEquals(null, languageManagerBean.viewLanguage(lexLanguageDTO.getId()));
  }

  @Test
  public void viewLanguageTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexLanguage.class, lexLanguageDTO.getId())).thenReturn(lexLanguage);
    Assertions.assertEquals(lexLanguage.getName(), languageManagerBean.viewLanguage(lexLanguageDTO.getId()).getName());
  }

  @Test
  public void getLanguageByLocaleTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getSingleResult()).thenReturn(lexLanguage);
    Assertions.assertEquals(lexLanguage.getName(),
        languageManagerBean.getLanguageByLocale(lexLanguageDTO.getLocale()).getName());
  }

  @Test
  public void updateLanguageTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexLanguage.class, lexLanguageDTO.getId())).thenReturn(lexLanguage);
    Assertions.assertEquals(lexLanguage.getName(), languageManagerBean.updateLanguage(lexLanguageDTO).getName());
  }

  @Test
  public void listLanguagesEmptyTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, languageManagerBean.listLanguages(pagingParams, true).length);
  }

  @Test
  public void listLanguagesEmptyOnlyActiveTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, languageManagerBean.listLanguages(pagingParams, false).length);
  }

  @Test
  public void listLanguagesTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(Arrays.asList(lexLanguages));
    Assertions.assertEquals(1, languageManagerBean.listLanguages(pagingParams, true).length);
  }

  @Test
  public void getEffectiveLanguageNullTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals("en", languageManagerBean.getEffectiveLanguage(EN_UK, EN));
  }

  @Test
  public void getEffectiveLanguageTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(Arrays.asList(lexLanguages));
    Assertions.assertEquals("en_uk", languageManagerBean.getEffectiveLanguage(EN_UK, EN));
  }

  @Test
  public void getEffectiveLanguageScenario2Test() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(Arrays.asList(lexLanguages));
    Assertions.assertEquals("en", languageManagerBean.getEffectiveLanguage(null, EN));
  }

  @Test
  public void downloadLanguageNullTest() throws QlackFuseLexiconException {
    Assertions.assertNotNull(languageManagerBean.downloadLanguage(EN));
  }

  @Test
  public void downloadLanguageTest() throws QlackFuseLexiconException {
    Mockito.when(keyManagerBean.getTranslationsForLocale(EN, null)).thenReturn(hashMap);
    Assertions.assertNotNull(languageManagerBean.downloadLanguage(EN));
  }

}
