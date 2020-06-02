package com.eurodyn.qlack.fuse.modules.scheduler.util;

/**
 * @author European Dynamics SA.
 */
public class Constants {

  public static final String QSCH_CLASS_NAME = "qsch.classname";
  public static final String QSCH_CLASS_ARRAY = "qsch.classarray";

  /**
   * Enumeration of the week days.
   */
  public enum TRIGGER_DAYS {
    MON, TUE, WED, THU, FRI, SAT, SUN
  }

  /**
   * Enumeration of the month period.
   */
  public enum TRIGGER_MONTH_PERIOD {
    FIRST, LAST
  }

  /**
   * Enumeration of the trigger fire types.
   */
  public enum TRIGGERS {
    ASAP, Daily, Monthly, Weekly, Cron
  }

  /**
   * Enumeration of the trigger mis-fire policies.
   */
  public enum TRIGGER_MISFIRE {
    // Instructs the Scheduler that upon a mis-fire situation, the CronTrigger wants to have
    // it's next-fire-time updated to the next time in the schedule after the current time
    // (taking into account any associated Calendar, but it does not want to be fired now.
    MISFIRE_INSTRUCTION_DO_NOTHING,
    // Instructs the Scheduler that upon a mis-fire situation, the CronTrigger wants to be fired
    // now by Scheduler.
    MISFIRE_INSTRUCTION_FIRE_ONCE_NOW,
    // Instructs the Scheduler that the Trigger will never be evaluated for a misfire situation,
    // and that the scheduler will simply try to fire it as soon as it can, and then update the
    // Trigger as if it had fired at the proper time. NOTE: if a trigger uses this instruction,
    // and it has missed several of its scheduled firings, then several rapid firings may occur
    // as the trigger attempt to catch back up to where it would have been. For example,
    // a SimpleTrigger that fires every 15 seconds which has misfired for 5 minutes will fire
    // 20 times once it gets the chance to fire.
    MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
  }

}
