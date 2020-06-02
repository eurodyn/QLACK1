package com.eurodyn.qlack.fuse.modules.mailing.service;

import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMessagesDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMsgAttachmentDTO;
import com.eurodyn.qlack.fuse.modules.mailing.exception.QlackFuseMailingException;
import javax.ejb.Remote;

import java.util.List;

/**
 * A Stateless Session EJB provides internal messages related services.
 *
 * Following use case are implemented by the stateless session EJB.
 *
 * Send internal message. Get internal INBOX folder messages. Get internal SENT folder messages.
 *
 * @author European Dynamics SA.
 */
@Remote
public interface InternalMessageManager {

  /**
   * Send internal message/email to the user.
   *
   * @param dto InternalMessagesDTO containing the message details.
   * @return InternalMessagesDTO containing the message details.
   */
  InternalMessagesDTO sendInternalMail(InternalMessagesDTO dto) throws QlackFuseMailingException;

  /**
   * Gets/Displays the Internal INBOX messages.
   *
   * @param userId user id of the user whose INBOX folder needs to be displayed.
   * @return List of INBOX messages.
   */
  List<InternalMessagesDTO> getInternalInboxFolder(String userId);

  /**
   * Gets/Displays the Internal SENT messages.
   *
   * @param userId user id of the user whose SENT messages folder needs to be displayed.
   * @return List of SENT messages.
   */
  List<InternalMessagesDTO> getInternalSentFolder(String userId);

  /**
   * This method marks message in INBOX as read.
   */
  void markMessageAsRead(String messageId);

  /**
   * This method marks message in INBOX as replied.
   */
  void markMessageAsReplied(String messageId);

  /**
   * This method marks message in INBOX as unread.
   */
  void markMessageAsUnread(String messageId);

  /**
   * This method deletes message from INBOX or SENT folder according to folder type.
   *
   * @param messageId Internal Message PK.
   */
  void deleteMessage(String messageId, String folderType);

  /**
   * This method views the email.
   *
   * @param messageId Internal Message PK.
   * @return InternalMessagesDTO
   */
  InternalMessagesDTO view(String messageId);

  /**
   * Returns the List of attachments for a internal message..
   *
   * @param messageId Internal Message PK.
   * @return List of internal message attachments.
   */
  List<InternalMsgAttachmentDTO> getInternalAttachments(String messageId);

  /**
   * Returns the internal attachment for saving in database.
   *
   * @return InternalMsgAttachmentDTO attachment for saving in db.
   */
  InternalMsgAttachmentDTO saveInternalAttachment(String attachmentId);

  /**
   * This method returns mail count for a User and for read/unread status of mail
   *
   * @param userId User Id of user (For null value count will return for all user)
   * @param readStatus It can be UNREAD or READ (For null value count will return for all mail)
   * @return Count of mails
   */
  long getMailCount(String userId, String readStatus);
}
