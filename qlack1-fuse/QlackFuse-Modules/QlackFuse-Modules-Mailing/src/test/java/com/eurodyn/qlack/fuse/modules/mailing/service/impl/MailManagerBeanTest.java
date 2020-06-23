package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import static com.eurodyn.qlack.fuse.modules.mailing.service.MailManager.EMAIL_STATUS.QUEUED;

import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO;
import com.eurodyn.qlack.fuse.modules.mailing.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiEmail;
import com.eurodyn.qlack.fuse.modules.mailing.service.MailManager.EMAIL_STATUS;
import com.eurodyn.qlack.fuse.modules.mailing.util.CriteriaBuilderUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.SneakyThrows;
import net.bzdyl.ejb3.criteria.Criteria;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Unit test class for MailManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class MailManagerBeanTest {

  private List<EmailDTO> emailDTOs;
  private EmailDTO emailDTO;
  private MaiEmail maiEmail;
  private List<MaiEmail> maiEmails;
  @InjectMocks
  private MailManagerBean mailManagerBean;
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
    emailDTOs = initTestValues.createEmailDTOs();
    emailDTO = initTestValues.createEmailDTO();
    maiEmail = initTestValues.createEmail();
    maiEmails = initTestValues.createMaiEmails();
  }

  @Test
  void queueEmailsTest() {
    mailManagerBean.queueEmails(emailDTOs);
    Mockito.verify(entityManager, Mockito.times(2)).persist(Mockito.any());
  }

  @Test
  void queueEmailsWithoutAttachmentsTest() {
    emailDTO.setAttachments(null);
    mailManagerBean.queueEmails(Arrays.asList(emailDTO));
    Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any());
  }

  @Test
  void queueEmailsWithEmptyAttachmentsTest() {
    emailDTO.setAttachments(new ArrayList<>());
    emailDTO.setDateSent(new Date().getTime());
    mailManagerBean.queueEmails(Arrays.asList(emailDTO));
    Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any());
  }

  @Test
  void deleteFromQueueTest() {
    Mockito.when(entityManager.find(MaiEmail.class, emailDTO.getId())).thenReturn(maiEmail);
    mailManagerBean.deleteFromQueue(emailDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).remove(maiEmail);
  }

  @Test
  void updateStatusTest() {
    Mockito.when(entityManager.find(MaiEmail.class, emailDTO.getId())).thenReturn(maiEmail);
    mailManagerBean.updateStatus(emailDTO.getId(), EMAIL_STATUS.FAILED);
    Mockito.verify(entityManager, Mockito.times(1)).find(MaiEmail.class, emailDTO.getId());
  }

  @Test
  void cleanupNullTest() {
    mockCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiEmails);
    mailManagerBean.cleanup(null, null);
    Mockito.verify(entityManager, Mockito.times(1)).remove(maiEmails.get(0));
  }

  @Test
  void cleanupEmptyStatusTest() {
    mockCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiEmails);
    mailManagerBean.cleanup(null, new EMAIL_STATUS[]{});
    Mockito.verify(entityManager, Mockito.times(1)).remove(maiEmails.get(0));
  }

  @Test
  void cleanupTest() {
    mockCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiEmails);
    mailManagerBean.cleanup(new Date().getTime(), new EMAIL_STATUS[]{QUEUED});
    Mockito.verify(entityManager, Mockito.times(1)).remove(maiEmails.get(0));
  }

  @SneakyThrows
  private void mockCriteriaBuilderUtil() {
    FieldSetter.setField(mailManagerBean,
        mailManagerBean.getClass().getDeclaredField("criteriaBuilderUtil"),
        criteriaBuilderUtil);
    Mockito.when(criteriaBuilderUtil.getCriteria()).thenReturn(criteria);
    Mockito.when(criteria.prepareQuery(entityManager)).thenReturn(query);
  }

}
