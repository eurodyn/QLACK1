package com.eurodyn.qlack.fuse.modules.mailing.dto;

import java.util.Date;
import java.util.List;

/**
 * Data transfer object for internal messages.
 *
 * @author European Dynamics SA.
 */
public class InternalMessagesDTO extends MailBaseDTO {

  private String subject;

  private String message;

  private String from;

  private String fromId;

  private String to;

  private String toId;

  private Date dateSent;

  private Date dateReceived;

  private String status;

  private String attachment;

  private List<InternalMsgAttachmentDTO> internalAttachments;

  private String type;

  private String fwdAttachmentId;

  private String deleteType;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getFromId() {
    return fromId;
  }

  public void setFromId(String fromId) {
    this.fromId = fromId;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getToId() {
    return toId;
  }

  public void setToId(String toId) {
    this.toId = toId;
  }

  public Date getDateSent() {
    return dateSent;
  }

  public void setDateSent(Date dateSent) {
    this.dateSent = dateSent;
  }

  public Date getDateReceived() {
    return dateReceived;
  }

  public void setDateReceived(Date dateReceived) {
    this.dateReceived = dateReceived;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAttachment() {
    return attachment;
  }

  public void setAttachment(String attachment) {
    this.attachment = attachment;
  }

  public List<InternalMsgAttachmentDTO> getInternalAttachments() {
    return internalAttachments;
  }

  public void setInternalAttachments(
      List<InternalMsgAttachmentDTO> internalAttachments) {
    this.internalAttachments = internalAttachments;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getFwdAttachmentId() {
    return fwdAttachmentId;
  }

  public void setFwdAttachmentId(String fwdAttachmentId) {
    this.fwdAttachmentId = fwdAttachmentId;
  }

  public String getDeleteType() {
    return deleteType;
  }

  public void setDeleteType(String deleteType) {
    this.deleteType = deleteType;
  }
}
