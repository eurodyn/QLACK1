package com.eurodyn.qlack.fuse.modules.scheduler.service;

import com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;
import javax.ejb.Remote;

/**
 * The Scheduler Manager interface.
 *
 * @author European Dynamics SA.
 */
@Remote
public interface SchedulerManager {

  /**
   * Schedules a job.
   *
   * @param job a SchedulerWrappedJob job with the appropriate job information
   * @param trigger a SchedulerWrappedTrigger trigger with the appropriate trigger information
   * @throws QlackFuseSchedulerException if an exception occurs while scheduleJob
   */
  void scheduleJob(
      com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerWrappedJob job,
      com.eurodyn.qlack.fuse.modules.scheduler.job.trigger.SchedulerWrappedTrigger trigger)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Schedules a job, in case that it has changed. Please note that this method only checks if the job class has changed
   * and does not reschedule the job if the job data map has changed but the job class has remained the same.
   *
   * @param job the SchedulerWrappedJob job with the appropriate job information
   * @param trigger the SchedulerWrappedTrigger trigger with the appropriate trigger information
   * @return true if the job has been scheduled/rescheduled, false otherwise
   * @throws QlackFuseSchedulerException if an exception occurs while scheduleJobIfDifferent
   */
  boolean scheduleJobIfDifferent(
      com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerWrappedJob job,
      com.eurodyn.qlack.fuse.modules.scheduler.job.trigger.SchedulerWrappedTrigger trigger)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Clears all the scheduler data.
   *
   * @throws QlackFuseSchedulerException if an exception occurs while clear
   */
  void clear()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Pauses all the jobs
   *
   * @throws QlackFuseSchedulerException if an exception occurs while pauseAll
   */
  void pauseAll()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Reports whether the Scheduler is shutdown.
   *
   * @throws QlackFuseSchedulerException if an exception occurs while isShutdown
   */
  boolean isShutdown()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Reports whether the Scheduler is started.
   *
   * @throws QlackFuseSchedulerException if an exception occurs while isStarted
   */
  boolean isStarted()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Reports whether the Scheduler is in stand-by mode.
   *
   * @throws QlackFuseSchedulerException if an exception occurs while isInStandbyMode
   */
  boolean isInStandbyMode()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Starts the scheduler. Please note that this method does not check if the scheduler is already started before
   * starting it. It is the caller's responsibility to make this check.
   *
   * @throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException if the scheduler cannot be
   * started
   */
  void start()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Stops the scheduler. Please note that this method does not check if the scheduler is already stopped before
   * stopping it. It is the caller's responsibility to make this check. The scheduler cannot be restarted after it is
   * stopped.
   *
   * @throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException if the scheduler cannot be
   * stopped
   */
  void stop()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Puts the scheduler in standby mode. Please note that this method does not check if the scheduler is already in
   * standby mode. It is the caller's responsibility to make this check. The scheduler can be restarted at any time
   * after being put in standby mode.
   *
   * @throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException if the scheduler cannot be
   * stopped
   */
  void standby()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Resume (un-pause) all the jobs.
   *
   * @throws QlackFuseSchedulerException if an exception occurs while resumeAll
   */
  void resumeAll()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Deletes a job.
   *
   * @param jobName the name of the job to be deleted
   * @param jobGroup the group that the job belongs to
   * @throws QlackFuseSchedulerException if an exception occurs while deleteJob
   */
  boolean deleteJob(String jobName, String jobGroup)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Deletes multiple jobs.
   *
   * @param jobs a Map<String, String> that stores the jobName and the jobGroup of the job
   * @throws QlackFuseSchedulerException if an exception occurs while deletedJobs
   */
  boolean deleteJobs(java.util.Map<String, String> jobs)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Get the details of the Job instance with the given key.
   *
   * @param jobName the name of the job
   * @param jobGroup the group that the job belongs to
   * @param includeDatamap whether to include a Datamap in the job details or not
   * @param includeSerialisedClass whether to include a serialized class in the job details or not
   * @throws QlackFuseSchedulerException if an exception occurs while getJobDetails
   */
  com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerWrappedJob getJobDetails(
      String jobName,
      String jobGroup, boolean includeDatamap, boolean includeSerialisedClass)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Returns the instance Id of the Scheduler.
   *
   * @throws QlackFuseSchedulerException if an exception occurs while getSchedulerInstanceID
   */
  String getSchedulerInstanceID()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Returns the name of the Scheduler
   *
   * @throws QlackFuseSchedulerException if any exception comes while getSchedulerName
   */
  String getSchedulerName()
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Gets the next execution date of the trigger.
   *
   * @param triggerName the trigger key
   * @param triggerGroup the group that the trigger belongs to
   * @throws QlackFuseSchedulerException if an exception occurs while getNextFireTimeForTrigger
   */
  java.util.Date getNextFiretimeForTrigger(String triggerName,
      String triggerGroup)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Gets the date information of the execution of a trigger.
   *
   * @param triggerName the trigger key
   * @param triggerGroup the group that the trigger belongs to
   * @throws QlackFuseSchedulerException if an exception occurs while getDateInfoForTrigger
   */
  com.eurodyn.qlack.fuse.modules.scheduler.dto.TriggerDateInfo getDateInfoForTrigger(
      String triggerName,
      String triggerGroup)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Pauses a job.
   *
   * @param jobName the job key
   * @param jobGroup the group that the job belongs to
   * @throws QlackFuseSchedulerException if an exception occurs while pauseJob
   */
  void pauseJob(String jobName, String jobGroup)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Pauses a trigger.
   *
   * @param triggerName the trigger key
   * @param triggerGroup the group that the trigger belongs to
   * @throws QlackFuseSchedulerException if an exception occurs while pauseTrigger
   */
  void pauseTrigger(String triggerName, String triggerGroup)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Deletes the Trigger with the given key, and stores the new given one - which must be associated with the same job
   *
   * @param oldTriggerName the key of the trigger to be rescheduled
   * @param oldTriggerGroup the group that the old trigger belongs to
   * @param trigger the new trigger
   * @throws QlackFuseSchedulerException if an exception occurs while rescheduleJob
   */
  void rescheduleJob(String oldTriggerName, String oldTriggerGroup,
      com.eurodyn.qlack.fuse.modules.scheduler.job.trigger.SchedulerWrappedTrigger trigger)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Triggers the identified job.
   *
   * @param jobName the job key
   * @param jobGroup the group that the job belongs to
   * @throws QlackFuseSchedulerException if an exception occurs while triggerJob
   */
  void triggerJob(String jobName, String jobGroup)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

  /**
   * Triggers the identified job.
   *
   * @param jobName the job key
   * @param jobGroup the group that the job belongs to
   * @param dataMap a Map with the job information
   * @throws QlackFuseSchedulerException if an exception occurs while triggerJob
   */
  void triggerJob(String jobName, String jobGroup,
      java.util.Map<String, Object> dataMap)
      throws com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;

}
