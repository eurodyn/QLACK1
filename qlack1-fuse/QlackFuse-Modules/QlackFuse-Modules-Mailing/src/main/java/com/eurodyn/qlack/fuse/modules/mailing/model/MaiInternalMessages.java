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
    name = "mai_internal_messages"
)
public class MaiInternalMessages implements Serializable {

  private String id;
  private String message;
  private String mailFrom;
  private String mailTo;
  private Long dateSent;
  private Long dateReceived;
  private String status;
  private String subject;
  private String deleteType;
  private Set<MaiInternalAttachment> maiInternalAttachments = new HashSet(0);

  public MaiInternalMessages() {
  }

  public MaiInternalMessages(String message, String mailFrom, String mailTo, String subject, String deleteType) {
    this.message = message;
    this.mailFrom = mailFrom;
    this.mailTo = mailTo;
    this.subject = subject;
    this.deleteType = deleteType;
  }

  public MaiInternalMessages(String message, String mailFrom, String mailTo, Long dateSent, Long dateReceived,
      String status, String subject, String deleteType, Set<MaiInternalAttachment> maiInternalAttachments) {
    this.message = message;
    this.mailFrom = mailFrom;
    this.mailTo = mailTo;
    this.dateSent = dateSent;
    this.dateReceived = dateReceived;
    this.status = status;
    this.subject = subject;
    this.deleteType = deleteType;
    this.maiInternalAttachments = maiInternalAttachments;
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
      name = "message",
      nullable = false,
      length = 65535
  )
  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Column(
      name = "mail_from",
      nullable = false,
      length = 36
  )
  public String getMailFrom() {
    return this.mailFrom;
  }

  public void setMailFrom(String mailFrom) {
    this.mailFrom = mailFrom;
  }

  @Column(
      name = "mail_to",
      nullable = false,
      length = 36
  )
  public String getMailTo() {
    return this.mailTo;
  }

  public void setMailTo(String mailTo) {
    this.mailTo = mailTo;
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
      name = "date_received"
  )
  public Long getDateReceived() {
    return this.dateReceived;
  }

  public void setDateReceived(Long dateReceived) {
    this.dateReceived = dateReceived;
  }

  @Column(
      name = "status",
      length = 7
  )
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Column(
      name = "subject",
      nullable = false,
      length = 100
  )
  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Column(
      name = "delete_type",
      nullable = false,
      length = 1
  )
  public String getDeleteType() {
    return this.deleteType;
  }

  public void setDeleteType(String deleteType) {
    this.deleteType = deleteType;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "messagesId"
  )
  public Set<MaiInternalAttachment> getMaiInternalAttachments() {
    return this.maiInternalAttachments;
  }

  public void setMaiInternalAttachments(Set<MaiInternalAttachment> maiInternalAttachments) {
    this.maiInternalAttachments = maiInternalAttachments;
  }
}
