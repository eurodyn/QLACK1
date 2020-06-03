package com.eurodyn.qlack.fuse.modules.scheduler.service.impl;

import com.eurodyn.qlack.fuse.modules.scheduler.dto.TriggerDateInfo;
import com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;
import com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerWrappedJob;
import com.eurodyn.qlack.fuse.modules.scheduler.job.trigger.SchedulerWrappedTrigger;
import com.eurodyn.qlack.fuse.modules.scheduler.service.SchedulerManager;
import javax.ejb.Stateless;

import java.util.Date;
import java.util.Map;

@Stateless(name = "SchedulerManagerBean")
public class SchedulerManagerBean implements SchedulerManager {

  @Override
  public void scheduleJob(SchedulerWrappedJob job, SchedulerWrappedTrigger trigger) throws QlackFuseSchedulerException {

  }

  @Override
  public boolean scheduleJobIfDifferent(SchedulerWrappedJob job, SchedulerWrappedTrigger trigger)
      throws QlackFuseSchedulerException {
    return false;
  }

  @Override
  public void clear() throws QlackFuseSchedulerException {

  }

  @Override
  public void pauseAll() throws QlackFuseSchedulerException {

  }

  @Override
  public boolean isShutdown() throws QlackFuseSchedulerException {
    return false;
  }

  @Override
  public boolean isStarted() throws QlackFuseSchedulerException {
    return false;
  }

  @Override
  public boolean isInStandbyMode() throws QlackFuseSchedulerException {
    return false;
  }

  @Override
  public void start() throws QlackFuseSchedulerException {

  }

  @Override
  public void stop() throws QlackFuseSchedulerException {

  }

  @Override
  public void standby() throws QlackFuseSchedulerException {

  }

  @Override
  public void resumeAll() throws QlackFuseSchedulerException {

  }

  @Override
  public boolean deleteJob(String jobName, String jobGroup) throws QlackFuseSchedulerException {
    return false;
  }

  @Override
  public boolean deleteJobs(Map<String, String> jobs) throws QlackFuseSchedulerException {
    return false;
  }

  @Override
  public SchedulerWrappedJob getJobDetails(String jobName, String jobGroup, boolean includeDatamap,
      boolean includeSerialisedClass) throws QlackFuseSchedulerException {
    return null;
  }

  @Override
  public String getSchedulerInstanceID() throws QlackFuseSchedulerException {
    return null;
  }

  @Override
  public String getSchedulerName() throws QlackFuseSchedulerException {
    return null;
  }

  @Override
  public Date getNextFiretimeForTrigger(String triggerName, String triggerGroup) throws QlackFuseSchedulerException {
    return null;
  }

  @Override
  public TriggerDateInfo getDateInfoForTrigger(String triggerName, String triggerGroup)
      throws QlackFuseSchedulerException {
    return null;
  }

  @Override
  public void pauseJob(String jobName, String jobGroup) throws QlackFuseSchedulerException {

  }

  @Override
  public void pauseTrigger(String triggerName, String triggerGroup) throws QlackFuseSchedulerException {

  }

  @Override
  public void rescheduleJob(String oldTriggerName, String oldTriggerGroup, SchedulerWrappedTrigger trigger)
      throws QlackFuseSchedulerException {

  }

  @Override
  public void triggerJob(String jobName, String jobGroup) throws QlackFuseSchedulerException {

  }

  @Override
  public void triggerJob(String jobName, String jobGroup, Map<String, Object> dataMap)
      throws QlackFuseSchedulerException {

  }
}
