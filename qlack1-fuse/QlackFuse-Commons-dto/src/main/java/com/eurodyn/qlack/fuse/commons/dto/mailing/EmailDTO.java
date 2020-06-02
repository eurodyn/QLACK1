package com.eurodyn.qlack.fuse.commons.dto.mailing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DTO class for QLACK Email class
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class EmailDTO implements Serializable {

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

  /**
   * @param toContact the toContact to set
   */
  public void setToContact(List<String> toContact) {
    this.toContact = toContact;
  }

  public void setToContact(String toContact) {
    if (this.toContact != null && !this.toContact.isEmpty()) {
      log.warn("You are directly setting an individual contact when your"
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
