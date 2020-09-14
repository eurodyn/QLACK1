package com.eurodyn.qlack.fuse.modules.mailing.dto;

/**
 * @author European Dynamics SA
 */
public class NewsletterScheduleDTO {

  private String id;

  private String newsletterID;

  private String scheduledFor;

  private boolean sent;

  private Long sentOn;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNewsletterID() {
    return newsletterID;
  }

  public void setNewsletterID(String newsletterID) {
    this.newsletterID = newsletterID;
  }

  public String getScheduledFor() {
    return scheduledFor;
  }

  public void setScheduledFor(String scheduledFor) {
    this.scheduledFor = scheduledFor;
  }

  public boolean isSent() {
    return sent;
  }

  public void setSent(boolean sent) {
    this.sent = sent;
  }

  public Long getSentOn() {
    return sentOn;
  }

  public void setSentOn(Long sentOn) {
    this.sentOn = sentOn;
  }
}
