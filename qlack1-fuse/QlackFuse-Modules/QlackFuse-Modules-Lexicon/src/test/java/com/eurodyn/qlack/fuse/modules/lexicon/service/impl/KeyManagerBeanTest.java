package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexKeyDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import com.eurodyn.qlack.fuse.modules.lexicon.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexData;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexKey;
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
import java.util.List;

/**
 * Unit test class for KeyManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
class KeyManagerBeanTest {

  private static LexKeyDTO lexKeyDTO;
  private static LexLanguageDTO[] lexLanguages;
  private static LexKey lexKey;
  private static LexKey[] lexKeys;
  private static List<LexData> lexDataList;
  @InjectMocks
  private KeyManagerBean keyManagerBean;
  @Mock
  private EntityManager entityManager;
  @Mock
  private LanguageManagerBean languageManagerBean;
  @Mock
  private PagingParams pagingParams;
  @Mock
  private Query query;

  @BeforeAll
  static void init() {
    InitTestValues initTestValues = new InitTestValues();
    lexKeyDTO = initTestValues.createLexKeyDTO();
    lexLanguages = initTestValues.createLexLanguageDTOs();
    lexKey = initTestValues.createLexKey();
    lexKeys = initTestValues.createLexKeys();
    lexDataList = initTestValues.createLexDataList();
  }

  @Test
  void createKeyTest() {
    Mockito.when(languageManagerBean.listLanguages(null, true)).thenReturn(lexLanguages);
    lexKeyDTO.setData(null);
    Assertions.assertNotNull(keyManagerBean.createKey(lexKeyDTO));
  }

  @Test
  void deleteKeyByIdTest() {
    keyManagerBean.deleteKeyByID(lexKeyDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).find(LexKey.class, lexKeyDTO.getId());
  }

  @Test
  void viewKeyByIdTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.find(LexKey.class, lexKeyDTO.getId())).thenReturn(lexKey);
    Assertions.assertEquals(lexKey.getId(), keyManagerBean.viewKeyByID(lexKeyDTO.getId()).getId());
  }

  @Test
  void renameKeyByIdTest() {
    Mockito.when(entityManager.find(LexKey.class, lexKeyDTO.getId())).thenReturn(lexKey);
    keyManagerBean.renameKeyByID(lexKeyDTO.getId(), "New Key Name", "New User");
    Mockito.verify(entityManager, Mockito.times(1)).find(LexKey.class, lexKeyDTO.getId());
  }

  @Test
  void listKeysByGroupIDEmptyTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, keyManagerBean.listKeysByGroupID(lexKeyDTO.getId(), pagingParams).length);
  }

  @Test
  void listKeysByGroupIDTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(Arrays.asList(lexKeys));
    Assertions.assertEquals(1, keyManagerBean.listKeysByGroupID(lexKeyDTO.getId(), pagingParams).length);
  }

  @Test
  public void searchKeysEmptyTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, keyManagerBean.searchKeys(lexKeyDTO.getGroupId(), "Search Term", pagingParams).length);
  }

  @Test
  void searchKeysTest() throws QlackFuseLexiconException {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(Arrays.asList(lexKeys));
    Assertions.assertEquals(1, keyManagerBean.searchKeys(lexKeyDTO.getGroupId(), "Search Term", pagingParams).length);
  }

  @Test
  void getTranslationsForGroupAndLocaleEmptyTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Assertions.assertEquals(0, keyManagerBean.getTranslationsForGroupAndLocale(lexKeyDTO.getGroupId(), "en").size());
  }

  @Test
  void getTranslationsForGroupAndLocaleTest() {
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getResultList()).thenReturn(lexDataList);
    Assertions.assertEquals(1, keyManagerBean.getTranslationsForGroupAndLocale(lexKeyDTO.getGroupId(), "en").size());
  }

}
