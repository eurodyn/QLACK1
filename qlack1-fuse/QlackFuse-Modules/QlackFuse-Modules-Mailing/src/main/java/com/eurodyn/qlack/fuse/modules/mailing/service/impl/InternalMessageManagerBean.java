package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMessagesDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMsgAttachmentDTO;
import com.eurodyn.qlack.fuse.modules.mailing.exception.QlackFuseMailingException;
import com.eurodyn.qlack.fuse.modules.mailing.service.InternalMessageManager;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Stateless(name = "InternalMessageManagerBean")
public class InternalMessageManagerBean implements InternalMessageManager {

  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager entityManager;

  @Resource(name = "QlackConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(name = "QlackNotificationTopic")
  private Topic notificationTopic;

  @Override
  public InternalMessagesDTO sendInternalMail(InternalMessagesDTO dto) throws QlackFuseMailingException {
    return null;
  }

  @Override
  public List<InternalMessagesDTO> getInternalInboxFolder(String userId) {
    return null;
  }

  @Override
  public List<InternalMessagesDTO> getInternalSentFolder(String userId) {
    return null;
  }

  @Override
  public void markMessageAsRead(String messageId) {

  }

  @Override
  public void markMessageAsReplied(String messageId) {

  }

  @Override
  public void markMessageAsUnread(String messageId) {

  }

  @Override
  public void deleteMessage(String messageId, String folderType) {

  }

  @Override
  public InternalMessagesDTO view(String messageId) {
    return null;
  }

  @Override
  public List<InternalMsgAttachmentDTO> getInternalAttachments(String messageId) {
    return null;
  }

  @Override
  public InternalMsgAttachmentDTO saveInternalAttachment(String attachmentId) {
    return null;
  }

  @Override
  public long getMailCount(String userId, String readStatus) {
    return 0;
  }
}
