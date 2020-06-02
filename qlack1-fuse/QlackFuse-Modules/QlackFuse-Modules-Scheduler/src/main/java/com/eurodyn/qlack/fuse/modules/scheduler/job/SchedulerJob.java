package com.eurodyn.qlack.fuse.modules.scheduler.job;

import java.util.Map;

/**
 * The Scheduler Job interface.
 *
 * @author European Dynamics SA.
 */
public interface SchedulerJob {

  /**
   * The execute method of the Scheduler job.
   *
   * @param dataMap - the data of the job to be scheduled.
   */
  void execute(Map<String, Object> dataMap);

}
