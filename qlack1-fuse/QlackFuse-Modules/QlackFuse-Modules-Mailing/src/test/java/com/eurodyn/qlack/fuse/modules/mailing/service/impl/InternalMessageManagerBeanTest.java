package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import static com.eurodyn.qlack.fuse.modules.mailing.util.MaiConstants.INBOX_FOLDER_TYPE;
import static com.eurodyn.qlack.fuse.modules.mailing.util.MaiConstants.SENT_FOLDER_TYPE;

import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMessagesDTO;
import com.eurodyn.qlack.fuse.modules.mailing.init.InitTestValues;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiInternalAttachment;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiInternalMessages;
import com.eurodyn.qlack.fuse.modules.mailing.util.CriteriaBuilderUtil;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;
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

import java.io.IOException;
import java.util.List;

/**
 * Unit test class for InternalMessageManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class InternalMessageManagerBeanTest {

  private static final String USER_ID = "user";
  @Mock
  private static CriteriaBuilderUtil messageCriteriaBuilderUtil;
  private InternalMessagesDTO internalMessagesDTO;
  private MaiInternalMessages maiInternalMessages;
  private List<MaiInternalMessages> maiInternalMessageList;
  private List<MaiInternalAttachment> maiInternalAttachments;
  private MaiInternalAttachment maiInternalAttachment;
  @InjectMocks
  private InternalMessageManagerBean internalMessageManagerBean;
  @Mock
  private EntityManager entityManager;
  @Mock
  private ConnectionFactory connectionFactory;
  @Mock
  private Topic topic;
  @Mock
  private CriteriaBuilderUtil attachmentCriteriaBuilderUtil;
  @Mock
  private Criteria criteria;
  @Mock
  private Query query;

  @BeforeAll
  void init() throws IOException {
    InitTestValues initTestValues = new InitTestValues();
    internalMessagesDTO = initTestValues.createInternalMessagesDTO();
    maiInternalMessages = initTestValues.createMaiInternalMessages();
    maiInternalMessageList = initTestValues.createMaiInternalMessagesList();
    maiInternalAttachments = initTestValues.createMaiInternalAttachments();
    maiInternalAttachment = initTestValues.createMaiInternalAttachment();
  }

  @Test
  void markMessageAsReadTest() {
    Mockito.when(entityManager.find(MaiInternalMessages.class, internalMessagesDTO.getId()))
        .thenReturn(maiInternalMessages);
    internalMessageManagerBean.markMessageAsRead(internalMessagesDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).merge(maiInternalMessages);
  }

  @Test
  void markMessageAsUnreadTest() {
    Mockito.when(entityManager.find(MaiInternalMessages.class, internalMessagesDTO.getId()))
        .thenReturn(maiInternalMessages);
    internalMessageManagerBean.markMessageAsUnread(internalMessagesDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).merge(maiInternalMessages);
  }

  @Test
  void markMessageAsRepliedTest() {
    Mockito.when(entityManager.find(MaiInternalMessages.class, internalMessagesDTO.getId()))
        .thenReturn(maiInternalMessages);
    internalMessageManagerBean.markMessageAsReplied(internalMessagesDTO.getId());
    Mockito.verify(entityManager, Mockito.times(1)).merge(maiInternalMessages);
  }

  @Test
  void deleteMessageInboxFalseTest() {
    Mockito.when(entityManager.find(MaiInternalMessages.class, internalMessagesDTO.getId()))
        .thenReturn(maiInternalMessages);
    internalMessageManagerBean.deleteMessage(internalMessagesDTO.getId(), INBOX_FOLDER_TYPE);
    Mockito.verify(entityManager, Mockito.times(1)).merge(maiInternalMessages);
  }

  @Test
  void deleteMessageInboxTrueTest() {
    maiInternalMessages.setDeleteType("S");
    Mockito.when(entityManager.find(MaiInternalMessages.class, internalMessagesDTO.getId()))
        .thenReturn(maiInternalMessages);
    internalMessageManagerBean.deleteMessage(internalMessagesDTO.getId(), INBOX_FOLDER_TYPE);
    Mockito.verify(entityManager, Mockito.times(1)).merge(maiInternalMessages);
  }

  @Test
  void deleteMessageSendFalseTest() {
    maiInternalMessages.setDeleteType(null);
    Mockito.when(entityManager.find(MaiInternalMessages.class, internalMessagesDTO.getId()))
        .thenReturn(maiInternalMessages);
    internalMessageManagerBean.deleteMessage(internalMessagesDTO.getId(), SENT_FOLDER_TYPE);
    Mockito.verify(entityManager, Mockito.times(1)).merge(maiInternalMessages);
  }

  @Test
  void deleteMessageSendTrueTest() {
    maiInternalMessages.setDeleteType("I");
    Mockito.when(entityManager.find(MaiInternalMessages.class, internalMessagesDTO.getId()))
        .thenReturn(maiInternalMessages);
    internalMessageManagerBean.deleteMessage(internalMessagesDTO.getId(), SENT_FOLDER_TYPE);
    Mockito.verify(entityManager, Mockito.times(1)).merge(maiInternalMessages);
  }

  @Test
  void viewTest() {
    Mockito.when(entityManager.find(MaiInternalMessages.class, internalMessagesDTO.getId()))
        .thenReturn(maiInternalMessages);
    Assertions.assertEquals(maiInternalMessages.getId(),
        internalMessageManagerBean.view(internalMessagesDTO.getId()).getId());
  }

  @Test
  void getInternalInboxFolderNullUserTest() throws NoSuchFieldException {
    mockMessageCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiInternalMessageList);
    Assertions.assertEquals(1, internalMessageManagerBean.getInternalInboxFolder(null).size());
  }

  @Test
  void getInternalInboxFolderTest() throws NoSuchFieldException {
    mockMessageCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiInternalMessageList);
    Assertions.assertEquals(1, internalMessageManagerBean.getInternalInboxFolder(USER_ID).size());
  }

  @Test
  void getMailCountNullTest() throws NoSuchFieldException {
    FieldSetter.setField(internalMessageManagerBean,
        internalMessageManagerBean.getClass().getDeclaredField("messageCriteriaBuilderUtil"),
        messageCriteriaBuilderUtil);
    Mockito.when(messageCriteriaBuilderUtil.getCriteria()).thenReturn(criteria);
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getSingleResult()).thenReturn(1L);
    Assertions.assertEquals(1, internalMessageManagerBean.getMailCount(null, null));
  }

  @Test
  void getMailCountTest() throws NoSuchFieldException {
    FieldSetter.setField(internalMessageManagerBean,
        internalMessageManagerBean.getClass().getDeclaredField("messageCriteriaBuilderUtil"),
        messageCriteriaBuilderUtil);
    Mockito.when(messageCriteriaBuilderUtil.getCriteria()).thenReturn(criteria);
    Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
    Mockito.when(query.getSingleResult()).thenReturn(1L);
    Assertions.assertEquals(1, internalMessageManagerBean.getMailCount(USER_ID, "SEND"));
  }

  @Test
  void getInternalSendFolderNullUserTest() throws NoSuchFieldException {
    mockMessageCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiInternalMessageList);
    Assertions.assertEquals(1, internalMessageManagerBean.getInternalSentFolder(null).size());
  }

  @Test
  void getInternalSendFolderTest() throws NoSuchFieldException {
    mockMessageCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiInternalMessageList);
    Assertions.assertEquals(1, internalMessageManagerBean.getInternalSentFolder(USER_ID).size());
  }

  @Test
  void getInternalAttachmentsNullTest() throws NoSuchFieldException {
    mockAttachmentCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(null);
    Assertions.assertEquals(0, internalMessageManagerBean.getInternalAttachments(null).size());
  }

  @Test
  void getInternalAttachmentsTest() throws NoSuchFieldException {
    mockAttachmentCriteriaBuilderUtil();
    Mockito.when(query.getResultList()).thenReturn(maiInternalAttachments);
    Assertions.assertEquals(1, internalMessageManagerBean.getInternalAttachments(maiInternalMessages.getId()).size());
  }

  @Test
  void saveInternalAttachmentNullTest() throws NoSuchFieldException {
    mockAttachmentCriteriaBuilderUtil();
    Mockito.when(query.getSingleResult()).thenReturn(maiInternalAttachment);
    Assertions.assertEquals(maiInternalAttachment.getId(),
        internalMessageManagerBean.saveInternalAttachment(null).getId());
  }

  @Test
  void saveInternalAttachmentTest() throws NoSuchFieldException {
    mockAttachmentCriteriaBuilderUtil();
    Mockito.when(query.getSingleResult()).thenReturn(maiInternalAttachment);
    Assertions.assertEquals(maiInternalAttachment.getId(),
        internalMessageManagerBean.saveInternalAttachment(maiInternalAttachment.getId()).getId());
  }

  private void mockMessageCriteriaBuilderUtil() throws NoSuchFieldException {
    FieldSetter.setField(internalMessageManagerBean,
        internalMessageManagerBean.getClass().getDeclaredField("messageCriteriaBuilderUtil"),
        messageCriteriaBuilderUtil);
    Mockito.when(messageCriteriaBuilderUtil.getCriteria()).thenReturn(criteria);
    Mockito.when(criteria.prepareQuery(entityManager)).thenReturn(query);
  }

  private void mockAttachmentCriteriaBuilderUtil() throws NoSuchFieldException {
    FieldSetter.setField(internalMessageManagerBean,
        internalMessageManagerBean.getClass().getDeclaredField("attachmentCriteriaBuilderUtil"),
        attachmentCriteriaBuilderUtil);
    Mockito.when(attachmentCriteriaBuilderUtil.getCriteria()).thenReturn(criteria);
    Mockito.when(criteria.prepareQuery(entityManager)).thenReturn(query);
  }

}
