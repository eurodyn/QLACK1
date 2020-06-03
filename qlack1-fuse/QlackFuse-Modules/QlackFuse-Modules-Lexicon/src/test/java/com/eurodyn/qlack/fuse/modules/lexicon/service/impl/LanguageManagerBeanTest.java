/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexLanguage;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.", "com.sun.org.apache.xerces.",
    "javax.xml.", "org.xml.", "org.w3c.dom.",
    "com.sun.org.apache.xalan.", "javax.activation.*"})
@PrepareForTest({LexLanguage.class})
public class LanguageManagerBeanTest {

  @Mock
  private Query query;

  private static MockServices mockServices = new MockServices();

  @Before
  public void prepareMocks() {
    PowerMockito.mockStatic(LexLanguage.class);
  }

  @Test
  public void testCreateLanguage() throws Exception {
    System.out.println("createLanguage");

    LexLanguageDTO lexLanguageDTO = new LexLanguageDTO();
    lexLanguageDTO.setName("Greek");
    lexLanguageDTO.setLocale("el_GR");
    lexLanguageDTO.setCreatedBy("system");
    lexLanguageDTO.setActive(Boolean.TRUE);

    LanguageManagerBean languageManagerBean = mockServices.getLanguageManagerBean();
    EntityManager em = mockServices.getEntityManager();

    when(languageManagerBean.createLanguage(lexLanguageDTO)).thenReturn(lexLanguageDTO);
    LexLanguageDTO result = languageManagerBean.createLanguage(lexLanguageDTO);

    when(em.createQuery(anyString())).thenReturn(query);
    Query q = em.createQuery("SELECT l FROM LexLanguage l WHERE l.id = :id");
    q.setParameter("id", result.getId());

    assertEquals(1, q.getResultList().size());
    LexLanguage entity = (LexLanguage) q.getResultList().get(0);

    assertEquals("Greek", entity.getName());
    assertEquals("el_GR", entity.getLocale());
  }

}
