package com.eurodyn.qlack.fuse.modules.scheduler.job.trigger;

import com.eurodyn.qlack.fuse.modules.scheduler.util.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * The scheduler wrapped trigger
 *
 * @author European Dynamics SA.
 */
public class SchedulerWrappedTrigger implements Serializable {

  //the trigger start date
  private Date startOn;

  //the trigger end date
  private Date endOn;

  //the trigger name
  private String triggerName;

  //the trigger group
  private String triggerGroup;

  //the trigger fire type (daily, Monthly, Weekly, etc)
  private Constants.TRIGGERS triggerType;

  //the trigger cron expression
  private String cronExpression;

  //the trigger daily time
  private String dailyTime;

  //the trigger day of the week
  private Constants.TRIGGER_DAYS weeklyDay;

  //the trigger monthly period
  private Constants.TRIGGER_MONTH_PERIOD monthlyPeriod;

  //the trigger mis-fire policy
  private Constants.TRIGGER_MISFIRE triggerMisfire;

  /**
   * Get the trigger start date.
   *
   * @return the startOn
   */
  public Date getStartOn() {
    return startOn;
  }

  /**
   * Set the trigger start date.
   *
   * @param startOn the startOn to set
   */
  public void setStartOn(Date startOn) {
    this.startOn = startOn;
  }

  /**
   * Get the trigger end date.
   *
   * @return the endOn
   */
  public Date getEndOn() {
    return endOn;
  }

  /**
   * Set the trigger end date.
   *
   * @param endOn the endOn to set
   */
  public void setEndOn(Date endOn) {
    this.endOn = endOn;
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

  /**
   * Get the trigger cron expression.
   *
   * @return the cronExpression
   */
  public String getCronExpression() {
    return cronExpression;
  }

  /**
   * Set the trigger cron expression.
   *
   * @param cronExpression the cronExpression to set
   */
  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  /**
   * Get the trigger fire type.
   *
   * @return the triggerType
   */
  public Constants.TRIGGERS getTriggerType() {
    return triggerType;
  }

  /**
   * Set the trigger fire type (daily, Monthly, Weekly, etc)
   *
   * @param triggerType the triggerType to set
   */
  public void setTriggerType(Constants.TRIGGERS triggerType) {
    this.triggerType = triggerType;
  }

  /**
   * Get the trigger fire time of the day.
   *
   * @return the dailyTime
   */
  public String getDailyTime() {
    return dailyTime;
  }

  /**
   * Set the trigger fire time of the day.
   *
   * @param dailyTime the dailyTime to set
   */
  public void setDailyTime(String dailyTime) {
    this.dailyTime = dailyTime;
  }

  /**
   * Get the trigger fire day of the week.
   *
   * @return the weeklyDay
   */
  public Constants.TRIGGER_DAYS getWeeklyDay() {
    return weeklyDay;
  }

  /**
   * Set the trigger fire day of the week.
   *
   * @param weeklyDay the weeklyDay to set
   */
  public void setWeeklyDay(Constants.TRIGGER_DAYS weeklyDay) {
    this.weeklyDay = weeklyDay;
  }

  /**
   * Get the trigger fire period of the month.
   *
   * @return the monthlyPeriod
   */
  public Constants.TRIGGER_MONTH_PERIOD getMonthlyPeriod() {
    return monthlyPeriod;
  }

  /**
   * Set the trigger fire period of the month.
   *
   * @param monthlyPeriod the monthlyPeriod to set
   */
  public void setMonthlyPeriod(Constants.TRIGGER_MONTH_PERIOD monthlyPeriod) {
    this.monthlyPeriod = monthlyPeriod;
  }

  /**
   * Get the trigger mis-fire policy.
   *
   * @return the triggerMisfire
   */
  public Constants.TRIGGER_MISFIRE getTriggerMisfire() {
    return triggerMisfire;
  }

  /**
   * Set the trigger mis-fire policy.
   *
   * @param triggerMisfire the triggerMisfire to set
   */
  public void setTriggerMisfire(Constants.TRIGGER_MISFIRE triggerMisfire) {
    this.triggerMisfire = triggerMisfire;
  }

}
