package com.eurodyn.qlack.fuse.commons.dto.mailing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * DTO class for QLACK Email class
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public class EmailDTO implements Serializable {

  private static final Logger logger = Logger.getLogger(EmailDTO.class.getName());

  private String id;
  private String messageId;
  private List<String> toContact;
  private List<String> bccContact;
  private List<String> ccContact;
  private String subject;
  private String body;
  private String status;
  private List<AttachmentDTO> attachments;
  private EMAIL_TYPE emailType;
  private Date dateSent;
  private String serverResponse;
  private String from;

  /**
   * Default Constructor
   */
  public EmailDTO() {
    this.emailType = EMAIL_TYPE.TEXT;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public List<String> getToContact() {
    return toContact;
  }

  public List<String> getBccContact() {
    return bccContact;
  }

  public void setBccContact(List<String> bccContact) {
    this.bccContact = bccContact;
  }

  public List<String> getCcContact() {
    return ccContact;
  }

  public void setCcContact(List<String> ccContact) {
    this.ccContact = ccContact;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<AttachmentDTO> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<AttachmentDTO> attachments) {
    this.attachments = attachments;
  }

  public EMAIL_TYPE getEmailType() {
    return emailType;
  }

  public void setEmailType(EMAIL_TYPE emailType) {
    this.emailType = emailType;
  }

  public Date getDateSent() {
    return dateSent;
  }

  public void setDateSent(Date dateSent) {
    this.dateSent = dateSent;
  }

  public String getServerResponse() {
    return serverResponse;
  }

  public void setServerResponse(String serverResponse) {
    this.serverResponse = serverResponse;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  /**
   * @param toContact the toContact to set
   */
  public void setToContact(List<String> toContact) {
    this.toContact = toContact;
  }

  public void setToContact(String toContact) {
    if (this.toContact != null && !this.toContact.isEmpty()) {
      logger.warning("You are directly setting an individual contact when your"
          + " recipients list is not empty. Your existing recipients list will be"
          + " replaced with this contact.");
    }
    List<String> l = new ArrayList<>();
    l.add(toContact);
    setToContact(l);
  }

  /**
   * @param dateSent the dateSent to set
   */
  public void setDateSent(Long dateSent) {
    if (dateSent != null) {
      this.dateSent = new Date(dateSent);
    }
  }

  public void resetAllRecipients() {
    this.toContact = null;
    this.ccContact = null;
    this.bccContact = null;
  }

  @Override
  public String toString() {
    return "DTO id is: " + getId() + "Subject is: " + getSubject()
        + "To contact List: "
        + (getToContact() != null ? getToContact().toString() : null)
        + "CC contact List: "
        + (getCcContact() != null ? getCcContact().toString() : null)
        + "BCC contact List: "
        + (getBccContact() != null ? getBccContact().toString() : null) + "body: "
        + body + "status: " + status + "Date sent: " + dateSent
        + "Server Response: " + serverResponse + "attachment: "
        + attachments + "email type: " + emailType + "message Id: "
        + messageId;
  }

  public enum EMAIL_TYPE {
    TEXT, HTML
  }
}
