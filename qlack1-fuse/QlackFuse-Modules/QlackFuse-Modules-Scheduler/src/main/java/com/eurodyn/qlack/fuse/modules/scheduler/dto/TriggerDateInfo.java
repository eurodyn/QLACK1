package com.eurodyn.qlack.fuse.modules.scheduler.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author European Dynamics SA.
 */
public class TriggerDateInfo implements Serializable {

  private Date endTime;

  private Date finalFireTime;

  private Date nextFireTime;

  private Date previousFireTime;

  private Date starTime;

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Date getFinalFireTime() {
    return finalFireTime;
  }

  public void setFinalFireTime(Date finalFireTime) {
    this.finalFireTime = finalFireTime;
  }

  public Date getNextFireTime() {
    return nextFireTime;
  }

  public void setNextFireTime(Date nextFireTime) {
    this.nextFireTime = nextFireTime;
  }

  public Date getPreviousFireTime() {
    return previousFireTime;
  }

  public void setPreviousFireTime(Date previousFireTime) {
    this.previousFireTime = previousFireTime;
  }

  public Date getStarTime() {
    return starTime;
  }

  public void setStarTime(Date starTime) {
    this.starTime = starTime;
  }
}
