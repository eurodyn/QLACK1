package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.commons.messaging.Messenger;
import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMessagesDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMsgAttachmentDTO;
import com.eurodyn.qlack.fuse.modules.mailing.exception.QlackFuseMailingException;
import com.eurodyn.qlack.fuse.modules.mailing.exception.QlackFuseMailingException.CODES;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiInternalAttachment;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiInternalMessages;
import com.eurodyn.qlack.fuse.modules.mailing.service.InternalMessageManager;
import com.eurodyn.qlack.fuse.modules.mailing.util.ConverterUtil;
import com.eurodyn.qlack.fuse.modules.mailing.util.CriteriaBuilderUtil;
import com.eurodyn.qlack.fuse.modules.mailing.util.MaiConstants;
import com.eurodyn.qlack.fuse.modules.mailing.util.MailingMessage;
import com.eurodyn.qlack.fuse.modules.mailing.util.PropertiesLoaderSingleton;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Stateless Session EJB provides internal messages related services. For details regarding the functionality offered
 * see the respective interfaces.
 *
 * @author European Dynamics SA.
 */
@Stateless(name = "InternalMessageManagerBean")
public class InternalMessageManagerBean implements InternalMessageManager {

  private static final Logger logger = Logger.getLogger(InternalMessageManagerBean.class.getName());
  private final CriteriaBuilderUtil messageCriteriaBuilderUtil = new CriteriaBuilderUtil(MaiConstants.INTERNAL_MSG);
  private final CriteriaBuilderUtil attachmentCriteriaBuilderUtil = new CriteriaBuilderUtil(
      MaiConstants.INTERNAL_ATTACHMENT);
  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager em;
  @Resource(name = "QlackConnectionFactory")
  private ConnectionFactory connectionFactory;
  @Resource(name = "QlackNotificationTopic")
  private Topic notificationTopic;

  /**
   * Send a new internal message.
   *
   * @param dto - the internal message data
   * @return - an InternalMessagesDTO object
   */
  @Override
  public InternalMessagesDTO sendInternalMail(InternalMessagesDTO dto)
      throws QlackFuseMailingException {

    MaiInternalMessages maiInternalMessages =
        ConverterUtil.converToInternalMessagesEntityWOAttachments(dto);

    //Status can be READ, UNREAD, REPLIED.
    maiInternalMessages.setStatus("UNREAD");
    maiInternalMessages.setDeleteType("N");

    em.persist(maiInternalMessages);

    List<InternalMsgAttachmentDTO> internalMsgAttachments =
        dto.getInternalAttachments();

    if (dto.getFwdAttachmentId() != null) {
      //gets the internal attachment for fwding.
      InternalMsgAttachmentDTO internalMsgAttachmentDTO =
          saveInternalAttachment(dto.getFwdAttachmentId());
      //creates new row for fwded attachment.
      internalMsgAttachmentDTO.setId(null);
      if (internalMsgAttachments == null) {
        internalMsgAttachments = new ArrayList<>();
      }
      internalMsgAttachments.add(internalMsgAttachmentDTO);
    }

    if (internalMsgAttachments != null) {
      logger.log(Level.FINEST, "internalMsgAttachments.size: ", internalMsgAttachments.size());

      for (InternalMsgAttachmentDTO attachment : internalMsgAttachments) {
        MaiInternalAttachment maiInternalAttachment =
            ConverterUtil.convertToInternalMsgAttachmentEntity(attachment);
        maiInternalAttachment.setMessagesId(maiInternalMessages);
        em.persist(maiInternalAttachment);
        logger.log(Level.FINEST, "maiInternalAttachment.filename: ",
            maiInternalAttachment.getFilename());
      }
    }
    // Post a notification about the event.
    if (PropertiesLoaderSingleton.getInstance()
        .getProperty("QlackFuse.Mailing.realtime.JMS.notifications").equals("true")) {
      MailingMessage message = new MailingMessage();
      message.setType(MailingMessage.MSGTYPE__MAIL_SENT);
      message.setSrcUserID(dto.getFromId());
      message.setStringProperty(MailingMessage.PRIVATE_USERID, dto.getToId());
      message.setStringProperty(MailingMessage.PROPERTY__TO_USER_ID, dto.getToId());
      try {
        Messenger.post(connectionFactory, notificationTopic, message);
      } catch (JMSException ex) {
        logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        throw new QlackFuseMailingException(CODES.ERR_MAI_0009, ex.getLocalizedMessage());
      }
    }

    return ConverterUtil.converToInternalMessagesDTO(maiInternalMessages);
  }

  /**
   * Get the Inbox. (when
   *
   * @param userId - the person that the message was sent to
   * @return a list of InternalMessagesDTO
   */
  @Override
  public List<InternalMessagesDTO> getInternalInboxFolder(String userId) {
    Criteria criteria = messageCriteriaBuilderUtil.getCriteria();
    if (userId != null) {
      criteria.add(Restrictions.eq(MaiConstants.INTERNAL_MSG_TO, userId));
      criteria.add(Restrictions.ne(MaiConstants.INTERNAL_MSG_DELETE_TYPE, "I"));
    }
    Query query = criteria.prepareQuery(em);

    List<MaiInternalMessages> maiInternalMessagesList = query.getResultList();
    List<InternalMessagesDTO> dtoList = ConverterUtil
        .convertToInternalMessagesDTOList(maiInternalMessagesList, em);

    for (InternalMessagesDTO dto : dtoList) {
      logger.log(Level.FINEST, "dto.getFromId()=", dto.getFromId());
      logger.log(Level.FINEST, "dto.getMessage()=", dto.getMessage());
      logger
          .log(Level.FINEST, "dto.getInternalAttachments()=", dto.getInternalAttachments().size());
    }

    return dtoList;
  }

  /**
   * Get the number of the messages
   *
   * @param userId - the person that the message was sent to
   * @param status - the status (read, unread, replied)
   * @return the No of messages.
   */
  @Override
  public long getMailCount(String userId, String status) {
    Criteria criteria = messageCriteriaBuilderUtil.getCriteria();
    if (userId != null) {
      criteria.add(Restrictions.eq(MaiConstants.INTERNAL_MSG_TO, userId));
      criteria.add(Restrictions.ne(MaiConstants.INTERNAL_MSG_DELETE_TYPE, "I"));
    }
    String whOrAnd = " where ";
    StringBuilder queryString = new StringBuilder(
        "select count(mi.id) from MaiInternalMessages mi ");
    if (userId != null) {
      queryString.append(whOrAnd).append(" mi.mailTo = :mailTo");
      queryString.append(" and mi.deleteType <> 'I' ");
      whOrAnd = " and ";
    }

    if (status != null) {
      queryString.append(whOrAnd).append(" UPPER(mi.status) = :status");
    }
    Query q = em.createQuery(queryString.toString());

    if (userId != null) {
      q.setParameter("mailTo", userId);
    }
    if (status != null) {
      q.setParameter("status", status.toUpperCase());
    }

    return (Long) q.getSingleResult();
  }

  /**
   * Get the sent folder.
   *
   * @param userId - the person that sent the message
   * @return List<InternalMessagesDTO> -  a list of messages
   */
  @Override
  public List<InternalMessagesDTO> getInternalSentFolder(String userId) {
    Criteria criteria = messageCriteriaBuilderUtil.getCriteria();
    if (userId != null) {
      criteria.add(Restrictions.eq(MaiConstants.INTERNAL_MSG_FRM, userId));
      criteria.add(Restrictions.ne(MaiConstants.INTERNAL_MSG_DELETE_TYPE, "S"));
    }
    Query query = criteria.prepareQuery(em);
    List<MaiInternalMessages> maiInternalMessagesList = query.getResultList();

    return ConverterUtil
        .convertToInternalMessagesDTOList(maiInternalMessagesList, em);
  }

  /**
   * Mark a message as Read
   *
   * @param messageId - the message Id
   */
  @Override
  public void markMessageAsRead(String messageId) {
    MaiInternalMessages maiInternalMessages = em.find(MaiInternalMessages.class, messageId);
    maiInternalMessages.setStatus(MaiConstants.MARK_READ);
    em.merge(maiInternalMessages);
  }

  /**
   * Mark a message as Replied.
   *
   * @param messageId - the message Id
   */
  @Override
  public void markMessageAsReplied(String messageId) {
    MaiInternalMessages maiInternalMessages = em.find(MaiInternalMessages.class, messageId);
    maiInternalMessages.setStatus(MaiConstants.MARK_REPLIED);
    em.merge(maiInternalMessages);
  }

  /**
   * Mark a  message as Unread.
   *
   * @param messageId - the message Id
   */
  @Override
  public void markMessageAsUnread(String messageId) {
    MaiInternalMessages maiInternalMessages = em.find(MaiInternalMessages.class, messageId);
    maiInternalMessages.setStatus(MaiConstants.MARK_UNREAD);
    em.merge(maiInternalMessages);
  }

  /**
   * Delete a message. Depending on the folder type (inbox or sent) this method perform the following: - if the folder
   * that contains the message is the inbox and the sender has already deleted the message, then the message is
   * permanently removed from the system. - if the folder that contains the message is the inbox and the sender has not
   * deleted the message, then the message is marked as "deleted from the sender". - if the folder that contains the
   * message is the sent folder and the receiver has already deleted the message, then the message is permanently
   * removed from the system. - if the folder that contains the message is the sent folder and the receiver has not
   * deleted the message, then the message is marked as "deleted from the receiver".
   *
   * @param messageId - the message Id
   * @param folderType - the folder type (inbox or sent)
   */
  @Override
  public void deleteMessage(String messageId, String folderType) {
    MaiInternalMessages maiInternalMessages = em.find(MaiInternalMessages.class, messageId);
    if (MaiConstants.INBOX_FOLDER_TYPE.equals(folderType)) {
      if ("S".equals(maiInternalMessages.getDeleteType())) {
        em.remove(em.merge(maiInternalMessages));
      } else {
        maiInternalMessages.setDeleteType("I");
        em.merge(maiInternalMessages);
      }
    }
    if (MaiConstants.SENT_FOLDER_TYPE.equals(folderType)) {
      if ("I".equals(maiInternalMessages.getDeleteType())) {
        em.remove(em.merge(maiInternalMessages));
      } else {
        maiInternalMessages.setDeleteType("S");
        em.merge(maiInternalMessages);
      }
    }
  }

  /**
   * View the details of a message.
   *
   * @param messageId - the message Id
   * @return the message
   */
  @Override
  public InternalMessagesDTO view(String messageId) {
    MaiInternalMessages maiInternalMessages = em.find(MaiInternalMessages.class, messageId);
    return ConverterUtil.converToInternalMessagesDTO(maiInternalMessages);
  }

  /**
   * Get the attachments of a message.
   *
   * @param messageId - the message Id
   */
  @Override
  public List<InternalMsgAttachmentDTO> getInternalAttachments(String messageId) {
    Criteria criteria = attachmentCriteriaBuilderUtil.getCriteria();
    if (messageId != null) {
      criteria.add(Restrictions.eq(MaiConstants.INTERNAL_ATTACHMENT_MAIL_ID, messageId));
    }
    Query query = criteria.prepareQuery(em);

    List<MaiInternalAttachment> maiInternalAttachments = query.getResultList();

    List<InternalMsgAttachmentDTO> internalMsgAttachmentDTOs = new ArrayList();
    if (maiInternalAttachments != null) {
      for (MaiInternalAttachment maiInternalAttachment : maiInternalAttachments) {
        internalMsgAttachmentDTOs
            .add(ConverterUtil.convertToInternalMsgAttachmentDTO(maiInternalAttachment));
      }
    }
    return internalMsgAttachmentDTOs;
  }

  /**
   * Get an attachment based on its Id.
   *
   * @param attachmentId - the attachment Id.
   * @return the attachment
   */
  @Override
  public InternalMsgAttachmentDTO saveInternalAttachment(String attachmentId) {
    Criteria criteria = attachmentCriteriaBuilderUtil.getCriteria();
    if (attachmentId != null) {
      criteria.add(Restrictions.eq(MaiConstants.INTERNAL_ATTACHMENT_ID, attachmentId));
    }
    Query query = criteria.prepareQuery(em);

    MaiInternalAttachment maiInternalAttachment = (MaiInternalAttachment) query.getSingleResult();

    return ConverterUtil.convertToInternalMsgAttachmentDTO(maiInternalAttachment);
  }
}
