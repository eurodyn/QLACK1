package com.eurodyn.qlack.fuse.modules.scheduler.job.trigger;

import java.io.Serializable;
import java.util.Date;

/**
 * The scheduler trigger
 *
 * @author European Dynamics SA.
 */
public abstract class SchedulerTrigger implements Serializable {

  //the trigger name
  private String triggerName;

  //the trigger group
  private String triggerGroup;

  //the trigger start date
  private Date startOn;

  //the trigger end date
  private Date endOn;

  /**
   * Get the trigger start date.
   *
   * @return the startOn
   */
  public Date getStartOn() {
    return startOn != null
        ? new Date(startOn.getTime())
        : null;
  }

  /**
   * Set the trigger start date.
   *
   * @param startOn the startOn to set
   */
  public void setStartOn(Date startOn) {
    this.startOn = new Date(startOn.getTime());
  }

  /**
   * Get the trigger end date.
   *
   * @return the endOn
   */
  public Date getEndOn() {
    return new Date(endOn.getTime());
  }

  /**
   * Set the trigger end date.
   *
   * @param endOn the endOn to set
   */
  public void setEndOn(Date endOn) {
    this.endOn = new Date(endOn.getTime());
  }

  /**
   * Get the trigger name.
   *
   * @return the triggerName
   */
  public String getTriggerName() {
    return triggerName;
  }

  /**
   * Set the trigger name.
   *
   * @param triggerName the triggerName to set
   */
  public void setTriggerName(String triggerName) {
    this.triggerName = triggerName;
  }

  /**
   * Get the trigger group.
   *
   * @return the triggerGroup
   */
  public String getTriggerGroup() {
    return triggerGroup;
  }

  /**
   * Set the trigger group.
   *
   * @param triggerGroup the triggerGroup to set
   */
  public void setTriggerGroup(String triggerGroup) {
    this.triggerGroup = triggerGroup;
  }

}
