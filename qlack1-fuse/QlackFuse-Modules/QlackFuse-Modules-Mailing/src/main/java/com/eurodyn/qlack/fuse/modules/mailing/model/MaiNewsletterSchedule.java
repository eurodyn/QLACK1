package com.eurodyn.qlack.fuse.modules.mailing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(
    name = "mai_newsletter_schedule",
    uniqueConstraints = {@UniqueConstraint(
        columnNames = {"newsletter_id"}
    )}
)
public class MaiNewsletterSchedule implements Serializable {

  private String id;
  private String newsletterId;
  private long scheduledFor;
  private boolean sent;
  private Long sentOn;

  public MaiNewsletterSchedule() {
  }

  public MaiNewsletterSchedule(String newsletterId, long scheduledFor, boolean sent) {
    this.newsletterId = newsletterId;
    this.scheduledFor = scheduledFor;
    this.sent = sent;
  }

  public MaiNewsletterSchedule(String newsletterId, long scheduledFor, boolean sent, Long sentOn) {
    this.newsletterId = newsletterId;
    this.scheduledFor = scheduledFor;
    this.sent = sent;
    this.sentOn = sentOn;
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
      name = "newsletter_id",
      unique = true,
      nullable = false,
      length = 36
  )
  public String getNewsletterId() {
    return this.newsletterId;
  }

  public void setNewsletterId(String newsletterId) {
    this.newsletterId = newsletterId;
  }

  @Column(
      name = "scheduledFor",
      nullable = false
  )
  public long getScheduledFor() {
    return this.scheduledFor;
  }

  public void setScheduledFor(long scheduledFor) {
    this.scheduledFor = scheduledFor;
  }

  @Column(
      name = "sent",
      nullable = false
  )
  public boolean isSent() {
    return this.sent;
  }

  public void setSent(boolean sent) {
    this.sent = sent;
  }

  @Column(
      name = "sentOn"
  )
  public Long getSentOn() {
    return this.sentOn;
  }

  public void setSentOn(Long sentOn) {
    this.sentOn = sentOn;
  }
}
