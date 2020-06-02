package com.eurodyn.qlack.fuse.modules.scheduler.util;

import com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerWrappedJob;
import com.eurodyn.qlack.fuse.modules.scheduler.job.trigger.SchedulerWrappedTrigger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;

/**
 * @author European Dynamics S.A.
 */
public class QuartzConverters {

  /**
   * Get a Quartz job object that contains all the details passed from the Scheduler wrapped job.
   *
   * @return a JobDetail object containing the details passed
   */
  public static JobDetail getQuartzJob(SchedulerWrappedJob job) {
    JobDataMap jdm = new JobDataMap();
    if (job.getDataMap() != null) {
      jdm.putAll(job.getDataMap());
    }
    jdm.put(Constants.QSCH_CLASS_NAME, job.getJobClassName());
    jdm.put(Constants.QSCH_CLASS_ARRAY, job.getJobClass());

    return JobBuilder.newJob(SchedulerJobRunner.class)
        .withIdentity(job.getJobName(), job.getJobGroup())
        .usingJobData(jdm)
        .storeDurably(job.isDurable())
        .build();
  }

  /**
   * Get a Quartz trigger object that contains all the details passed from the Scheduler wrapped trigger.
   *
   * @param trigger - the Scheduler wrapped trigger passed
   * @param jobName - the job's name
   * @param jobGroup - the job's group
   * @throws ParseException might be thrown during the parsing of Quartz CronExpression.
   */
  @SuppressWarnings({"unchecked"})
  public static Trigger getQuartzTrigger(SchedulerWrappedTrigger trigger, String jobName,
      String jobGroup) throws ParseException {
    TriggerBuilder tb = TriggerBuilder.newTrigger();
    tb.startAt(trigger.getStartOn());
    if (trigger.getEndOn() != null) {
      tb.endAt(trigger.getEndOn());
    }
    tb.withIdentity(trigger.getTriggerName(), trigger.getTriggerGroup());
    tb.forJob(jobName, jobGroup);

    CronExpression ce = null;
    switch (trigger.getTriggerType()) {
      case ASAP:
        break;
      case Daily:
        ce = new CronExpression("0 " + trigger.getDailyTime().substring(3) + " " +
            trigger.getDailyTime().substring(0, 2) + " ? * *");
        break;
      case Weekly:
        ce = new CronExpression("0 " + trigger.getDailyTime().substring(3) + " "
            + trigger.getDailyTime().substring(0, 2) + " ? * " + trigger.getWeeklyDay().toString());
        break;
      case Monthly:
        String dom = "";
        switch (trigger.getMonthlyPeriod()) {
          case FIRST:
            dom = "1";
            break;
          case LAST:
            dom = "L";
            break;
        }
        ce = new CronExpression("0 " + trigger.getDailyTime().substring(3) + " "
            + trigger.getDailyTime().substring(0, 2) + " " + dom + " * ?");
        break;
      case Cron:
        ce = new CronExpression(trigger.getCronExpression());
        break;
    }

    if (ce != null) {
      CronTriggerImpl cti = new CronTriggerImpl();
      cti.setCronExpression(ce);
      cti.setMisfireInstruction(setMisfireInstruction(trigger));
      tb.withSchedule(cti.getScheduleBuilder());
    }

    return tb.build();
  }

  /**
   * Set misfire instruction for trigger.
   */
  private static int setMisfireInstruction(SchedulerWrappedTrigger trigger) {
    int retVal = CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY;

    if (trigger.getTriggerMisfire() != null) {
      switch (trigger.getTriggerMisfire()) {
        case MISFIRE_INSTRUCTION_DO_NOTHING:
          retVal = CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING;
          break;
        case MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
          retVal = CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW;
          break;
        case MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
          retVal = CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY;
          break;
      }
    }

    return retVal;
  }

}
