package com.eurodyn.qlack.fuse.modules.scheduler.builder;

import com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;
import com.eurodyn.qlack.fuse.modules.scheduler.job.trigger.SchedulerWrappedTrigger;
import com.eurodyn.qlack.fuse.modules.scheduler.util.Constants;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


/**
 * This is the scheduler builder class for a trigger.
 *
 * @author European Dynamics SA.
 */
public class SchedulerTriggerBuilder {

  //the trigger's start date
  private Date startOn;

  //the trigger's end date
  private Date endOn;

  //the trigger's name
  private String triggerName;

  //the trigger's group
  private String triggerGroup;

  //the trigger's run interval type (daily, weekly, etc.)
  private Constants.TRIGGERS triggerType;

  //the trigger's cron expression
  private String cronExpression;

  //the time of the day that the trigger will run (if daily)
  private String dailyTime;

  //the day of the week that the trigger will run (if weekly)
  private Constants.TRIGGER_DAYS weeklyDay;

  //the period of the month (first, last) that the trigger will run (if montlhy)
  private Constants.TRIGGER_MONTH_PERIOD monthlyPeriod;

  //the trigger's mis-fire action to be taken
  private Constants.TRIGGER_MISFIRE triggerMisfire;

  /**
   * default constructor for the scheduler trigger builder
   */
  private SchedulerTriggerBuilder() {
  }

  /**
   * Create new trigger.
   */
  public static SchedulerTriggerBuilder newTrigger() {
    return new SchedulerTriggerBuilder();
  }

  /**
   * Set the trigger run interval type.
   *
   * @param triggerType - run interval type (daily, weekly, etc.)
   */
  public SchedulerTriggerBuilder withTrigger(Constants.TRIGGERS triggerType) {
    this.triggerType = triggerType;
    return this;
  }

  /**
   * Set the trigger mis-fire policy.
   *
   * @param triggerMisfire - the mis-fire policy
   */
  public SchedulerTriggerBuilder misfirePolicy(Constants.TRIGGER_MISFIRE triggerMisfire) {
    this.triggerMisfire = triggerMisfire;
    return this;
  }

  /**
   * Set the trigger cron expression.
   *
   * @param cronExpression - the cron expression
   */
  public SchedulerTriggerBuilder withCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
    return this;
  }

  /**
   * Set the trigger start date.
   *
   * @param startOn - the start date
   */
  public SchedulerTriggerBuilder startOn(Date startOn) {
    this.startOn = new Date(startOn.getTime());
    return this;
  }

  /**
   * Set the trigger end date.
   *
   * @param endOn - the end date
   */
  public SchedulerTriggerBuilder endOn(Date endOn) {
    this.endOn = new Date(endOn.getTime());
    return this;
  }

  /**
   * Set the trigger Identity (trigger name and trigger group).
   *
   * @param triggerName - the trigger name
   * @param triggerGroup - the trigger group
   */
  public SchedulerTriggerBuilder withIdentity(String triggerName, String triggerGroup) {
    this.triggerName = triggerName;
    this.triggerGroup = triggerGroup;
    return this;
  }

  /**
   * Set the trigger daily time (if the trigger runs on a daily, weekly or monthly basis only).
   *
   * @param dailyTime - the time (in HH:mm format, 5 digits)
   */
  public SchedulerTriggerBuilder dailyTime(String dailyTime) {
    this.dailyTime = dailyTime;
    return this;
  }

  /**
   * Set the trigger monthly period (if the trigger runs on a monthly basis only).
   *
   * @param monthlyPeriod - the monthly period
   */
  public SchedulerTriggerBuilder monthlyPeriod(Constants.TRIGGER_MONTH_PERIOD monthlyPeriod) {
    this.monthlyPeriod = monthlyPeriod;
    return this;
  }

  /**
   * Set the trigger weekly day (if the trigger runs on a weekly basis only).
   *
   * @param weeklyDay - the day of the week
   */
  public SchedulerTriggerBuilder weeklyDay(Constants.TRIGGER_DAYS weeklyDay) {
    this.weeklyDay = weeklyDay;
    return this;
  }

  /**
   * Build the new trigger.
   */
  public SchedulerWrappedTrigger build() throws QlackFuseSchedulerException {
    checkTriggerData();

    SchedulerWrappedTrigger swt = new SchedulerWrappedTrigger();
    if (StringUtils.isEmpty(triggerName)) {
      swt.setTriggerName("Trigger_" + UUID.randomUUID().toString());
    } else {
      swt.setTriggerName(triggerName);
    }
    if (StringUtils.isEmpty(triggerGroup)) {
      swt.setTriggerGroup("TriggerGroup_" + UUID.randomUUID().toString());
    } else {
      swt.setTriggerGroup(triggerGroup);
    }
    swt.setStartOn(Objects.requireNonNullElseGet(startOn, () -> Calendar.getInstance().getTime()));
    if (endOn != null) {
      swt.setEndOn(endOn);
    }
    if (!StringUtils.isEmpty(cronExpression)) {
      swt.setCronExpression(cronExpression);
    }
    swt.setTriggerType(triggerType);
    swt.setDailyTime(dailyTime);
    swt.setWeeklyDay(weeklyDay);
    swt.setMonthlyPeriod(monthlyPeriod);
    swt.setTriggerMisfire(triggerMisfire);

    return swt;
  }

  /**
   * Check that the trigger data passed, are valid.
   */
  private void checkTriggerData() throws QlackFuseSchedulerException {
    if (triggerType == null) {
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0005,
          "Trigger does not have a type.");
    }
    switch (triggerType) {
      case Daily:
        if (StringUtils.isEmpty(dailyTime)) {
          throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0005,
              "Trigger type 'Daily' requires a 'dailyTime' attribute defined.");
        }
        if ((dailyTime.length() < 5) || (dailyTime.indexOf(':') != 2)) {
          throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0005,
              "Trigger type 'Daily' requires a 'dailyTime' attribute defined as HH:mi, i.e. 07:19.");
        }
        break;
      case Weekly:
        if (StringUtils.isEmpty(dailyTime)) {
          throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0005,
              "Trigger type 'Weekly' requires a 'dailyTime' attribute defined.");
        }
        if (weeklyDay == null) {
          throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0005,
              "Trigger type 'Weekly' requires a 'weeklyDay' attribute defined.");
        }
        break;
      case Monthly:
        if (StringUtils.isEmpty(dailyTime)) {
          throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0005,
              "Trigger type 'Monthly' requires a 'dailyTime' attribute defined.");
        }
        if (monthlyPeriod == null) {
          throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0005,
              "Trigger type 'Monthly' requires a 'monthlyPeriod' attribute defined.");
        }
        break;
      case Cron:
        if (StringUtils.isEmpty(cronExpression)) {
          throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0005,
              "Trigger type 'Cron' requires a 'cronExpression' attribute defined.");
        }
        break;
      default:
        break;
    }
  }
}
