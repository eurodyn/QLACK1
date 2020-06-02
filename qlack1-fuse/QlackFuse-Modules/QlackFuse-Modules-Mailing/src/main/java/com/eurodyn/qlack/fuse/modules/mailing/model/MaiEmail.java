package com.eurodyn.qlack.fuse.modules.mailing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
    name = "mai_email"
)
public class MaiEmail implements Serializable {

  private String id;
  private String subject;
  private String body;
  private String fromEmail;
  private String toEmails;
  private String ccEmails;
  private String bccEmails;
  private String status;
  private String serverResponse;
  private String emailType;
  private Long dateSent;
  private Long serverResponseDate;
  private long addedOnDate;
  private byte retries;
  private byte tries;
  private Set<MaiAttachment> maiAttachments = new HashSet(0);

  public MaiEmail() {
  }

  public MaiEmail(long addedOnDate, byte retries, byte tries) {
    this.addedOnDate = addedOnDate;
    this.retries = retries;
    this.tries = tries;
  }

  public MaiEmail(String subject, String body, String fromEmail, String toEmails, String ccEmails, String bccEmails,
      String status, String serverResponse, String emailType, Long dateSent, Long serverResponseDate, long addedOnDate,
      byte retries, byte tries, Set<MaiAttachment> maiAttachments) {
    this.subject = subject;
    this.body = body;
    this.fromEmail = fromEmail;
    this.toEmails = toEmails;
    this.ccEmails = ccEmails;
    this.bccEmails = bccEmails;
    this.status = status;
    this.serverResponse = serverResponse;
    this.emailType = emailType;
    this.dateSent = dateSent;
    this.serverResponseDate = serverResponseDate;
    this.addedOnDate = addedOnDate;
    this.retries = retries;
    this.tries = tries;
    this.maiAttachments = maiAttachments;
  }

  @Id
  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(
      name = "subject",
      length = 254
  )
  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Column(
      name = "body"
  )
  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Column(
      name = "from_email"
  )
  public String getFromEmail() {
    return this.fromEmail;
  }

  public void setFromEmail(String fromEmail) {
    this.fromEmail = fromEmail;
  }

  @Column(
      name = "to_emails",
      length = 1024
  )
  public String getToEmails() {
    return this.toEmails;
  }

  public void setToEmails(String toEmails) {
    this.toEmails = toEmails;
  }

  @Column(
      name = "cc_emails",
      length = 1024
  )
  public String getCcEmails() {
    return this.ccEmails;
  }

  public void setCcEmails(String ccEmails) {
    this.ccEmails = ccEmails;
  }

  @Column(
      name = "bcc_emails",
      length = 1024
  )
  public String getBccEmails() {
    return this.bccEmails;
  }

  public void setBccEmails(String bccEmails) {
    this.bccEmails = bccEmails;
  }

  @Column(
      name = "status",
      length = 32
  )
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Column(
      name = "server_response",
      length = 1024
  )
  public String getServerResponse() {
    return this.serverResponse;
  }

  public void setServerResponse(String serverResponse) {
    this.serverResponse = serverResponse;
  }

  @Column(
      name = "email_type",
      length = 64
  )
  public String getEmailType() {
    return this.emailType;
  }

  public void setEmailType(String emailType) {
    this.emailType = emailType;
  }

  @Column(
      name = "date_sent"
  )
  public Long getDateSent() {
    return this.dateSent;
  }

  public void setDateSent(Long dateSent) {
    this.dateSent = dateSent;
  }

  @Column(
      name = "server_response_date"
  )
  public Long getServerResponseDate() {
    return this.serverResponseDate;
  }

  public void setServerResponseDate(Long serverResponseDate) {
    this.serverResponseDate = serverResponseDate;
  }

  @Column(
      name = "added_on_date",
      nullable = false
  )
  public long getAddedOnDate() {
    return this.addedOnDate;
  }

  public void setAddedOnDate(long addedOnDate) {
    this.addedOnDate = addedOnDate;
  }

  @Column(
      name = "retries",
      nullable = false
  )
  public byte getRetries() {
    return this.retries;
  }

  public void setRetries(byte retries) {
    this.retries = retries;
  }

  @Column(
      name = "tries",
      nullable = false
  )
  public byte getTries() {
    return this.tries;
  }

  public void setTries(byte tries) {
    this.tries = tries;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "emailId"
  )
  public Set<MaiAttachment> getMaiAttachments() {
    return this.maiAttachments;
  }

  public void setMaiAttachments(Set<MaiAttachment> maiAttachments) {
    this.maiAttachments = maiAttachments;
  }
}
