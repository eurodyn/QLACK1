package com.eurodyn.qlack.fuse.modules.scheduler.service.impl;

import com.eurodyn.qlack.fuse.modules.scheduler.dto.TriggerDateInfo;
import com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;
import com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerWrappedJob;
import com.eurodyn.qlack.fuse.modules.scheduler.job.trigger.SchedulerWrappedTrigger;
import com.eurodyn.qlack.fuse.modules.scheduler.service.SchedulerManager;
import com.eurodyn.qlack.fuse.modules.scheduler.util.Constants;
import com.eurodyn.qlack.fuse.modules.scheduler.util.QuartzConverters;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless EJB implementing the SchedulerManager Interface.
 *
 * @author European Dynamics SA.
 */
@Stateless(name = "SchedulerManagerBean")
public class SchedulerManagerBean implements SchedulerManager {

  private static final Logger logger = Logger.getLogger(SchedulerManagerBean.class.getName());
  @EJB
  private SchedulerMonitorBean schedulerMonitor;

  /**
   * {@inheritDoc}
   *
   * @param job {@inheritDoc}
   * @param trigger {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void scheduleJob(SchedulerWrappedJob job, SchedulerWrappedTrigger trigger)
      throws QlackFuseSchedulerException {
    try {
      logger.log(Level.FINE, "Scheduling job ''{1}.{0}'' with trigger ''{3}.{2}''.",
          new String[]{job.getJobName(), job.getJobGroup(), trigger.getTriggerName(),
              trigger.getTriggerGroup()});
      schedulerMonitor.
          getSchedulerInstance().
          scheduleJob(
              QuartzConverters.getQuartzJob(job),
              QuartzConverters.getQuartzTrigger(trigger, job.getJobName(), job.getJobGroup()));
    } catch (ParseException | SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0002,
          MessageFormat.format("Could not schedule job: {0}", ex.getLocalizedMessage()));
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param job {@inheritDoc}
   * @param trigger {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public boolean scheduleJobIfDifferent(SchedulerWrappedJob job, SchedulerWrappedTrigger trigger)
      throws QlackFuseSchedulerException {
    // First check if there is another job with the same identity already scheduled. If that job's .class is
    // different to the one currently submitted, replace it.
    boolean proceedScheduling = false;
    SchedulerWrappedJob oldJob = getJobDetails(job.getJobName(), job.getJobGroup(), true, true);
    if (oldJob != null) {
      if (!Arrays.equals(oldJob.getJobClass(), job.getJobClass())) {
        deleteJob(oldJob.getJobName(), oldJob.getJobGroup());
        proceedScheduling = true;
      }
    } else {
      proceedScheduling = true;
    }

    if (proceedScheduling) {
      scheduleJob(job, trigger);
    }

    return proceedScheduling;
  }

  /**
   * {@inheritDoc}
   *
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void clear() throws QlackFuseSchedulerException {
    try {
      schedulerMonitor.getSchedulerInstance().clear();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void pauseAll() throws QlackFuseSchedulerException {
    try {
      schedulerMonitor.getSchedulerInstance().pauseAll();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public boolean isShutdown() throws QlackFuseSchedulerException {
    try {
      return schedulerMonitor.getSchedulerInstance().isShutdown();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public boolean isStarted() throws QlackFuseSchedulerException {
    try {
      return schedulerMonitor.getSchedulerInstance().isStarted();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public boolean isInStandbyMode() throws QlackFuseSchedulerException {
    try {
      return schedulerMonitor.getSchedulerInstance().isInStandbyMode();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void start() throws QlackFuseSchedulerException {
    schedulerMonitor.startScheduler();
  }

  /**
   * {@inheritDoc}
   *
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void stop() throws QlackFuseSchedulerException {
    schedulerMonitor.shutdownScheduler();
  }

  /**
   * {@inheritDoc}
   *
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void standby() throws QlackFuseSchedulerException {
    try {
      schedulerMonitor.getSchedulerInstance().standby();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void resumeAll() throws QlackFuseSchedulerException {
    try {
      schedulerMonitor.getSchedulerInstance().resumeAll();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param jobName {@inheritDoc}
   * @param jobGroup {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public boolean deleteJob(String jobName, String jobGroup) throws QlackFuseSchedulerException {
    try {
      return schedulerMonitor.getSchedulerInstance()
          .deleteJob(JobKey.jobKey(jobName, jobGroup));
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param jobs {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public boolean deleteJobs(Map<String, String> jobs) throws QlackFuseSchedulerException {
    boolean retVal = true;

    try {
      for (String nextJob : jobs.keySet()) {
        retVal = retVal && schedulerMonitor.getSchedulerInstance()
            .deleteJob(JobKey.jobKey(nextJob, jobs.get(nextJob)));
      }
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }

    return retVal;
  }

  /**
   * {@inheritDoc}
   *
   * @param jobName {@inheritDoc}
   * @param jobGroup {@inheritDoc}
   * @param includeDatamap {@inheritDoc}
   * @param includeSerialisedClass {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public SchedulerWrappedJob getJobDetails(String jobName, String jobGroup, boolean includeDatamap,
      boolean includeSerialisedClass) throws QlackFuseSchedulerException {
    SchedulerWrappedJob retVal = null;

    try {
      JobDetail jobDetail = schedulerMonitor.getSchedulerInstance()
          .getJobDetail(JobKey.jobKey(jobName, jobGroup));
      if (jobDetail != null) {
        retVal = new SchedulerWrappedJob();
        retVal.setJobName(jobName);
        retVal.setJobGroup(jobGroup);
        retVal.setJobClassName(jobDetail.getJobDataMap().getString(Constants.QSCH_CLASS_NAME));
        if (includeSerialisedClass) {
          retVal.setJobClass((byte[]) jobDetail.getJobDataMap().get(Constants.QSCH_CLASS_ARRAY));
        }
        if (includeDatamap) {
          retVal.setDataMap(jobDetail.getJobDataMap());
          // Remobe from datamap the serialised class, as it is already encapsulated into the SchedulerWrappedJob.
          retVal.getDataMap().remove(Constants.QSCH_CLASS_ARRAY);
          retVal.getDataMap().remove(Constants.QSCH_CLASS_NAME);
        }
      }
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }

    return retVal;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public String getSchedulerInstanceID() throws QlackFuseSchedulerException {
    try {
      return schedulerMonitor.getSchedulerInstance().getSchedulerInstanceId();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public String getSchedulerName() throws QlackFuseSchedulerException {
    try {
      return schedulerMonitor.getSchedulerInstance().getSchedulerName();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * Gets the trigger instance with the given key and the given trigger group.
   *
   * @param triggerName the trigger key
   * @param triggerGroup the group that the trigger belongs to
   * @throws SchedulerException if an exception occurs while getTrigger
   */
  private Trigger getTrigger(String triggerName, String triggerGroup) throws SchedulerException {
    return schedulerMonitor.getSchedulerInstance()
        .getTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
  }

  /**
   * {@inheritDoc}
   *
   * @param triggerName {@inheritDoc}
   * @param triggerGroup {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public Date getNextFiretimeForTrigger(String triggerName, String triggerGroup)
      throws QlackFuseSchedulerException {
    try {
      return getTrigger(triggerName, triggerGroup).getNextFireTime();
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param triggerName {@inheritDoc}
   * @param triggerGroup {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public TriggerDateInfo getDateInfoForTrigger(String triggerName, String triggerGroup)
      throws QlackFuseSchedulerException {
    TriggerDateInfo retVal = new TriggerDateInfo();
    try {
      Trigger trigger = getTrigger(triggerName, triggerGroup);
      retVal.setEndTime(trigger.getEndTime());
      retVal.setFinalFireTime(trigger.getFinalFireTime());
      retVal.setNextFireTime(trigger.getNextFireTime());
      retVal.setPreviousFireTime(trigger.getPreviousFireTime());
      retVal.setStarTime(trigger.getStartTime());
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }

    return retVal;
  }

  /**
   * {@inheritDoc}
   *
   * @param jobName {@inheritDoc}
   * @param jobGroup {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void pauseJob(String jobName, String jobGroup) throws QlackFuseSchedulerException {
    try {
      schedulerMonitor.getSchedulerInstance().pauseJob(JobKey.jobKey(jobName, jobGroup));
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param triggerName {@inheritDoc}
   * @param triggerGroup {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void pauseTrigger(String triggerName, String triggerGroup)
      throws QlackFuseSchedulerException {
    try {
      schedulerMonitor.getSchedulerInstance()
          .pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param oldTriggerName {@inheritDoc}
   * @param oldTriggerGroup {@inheritDoc}
   * @param trigger {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void rescheduleJob(String oldTriggerName, String oldTriggerGroup,
      SchedulerWrappedTrigger trigger) throws QlackFuseSchedulerException {
    try {
      schedulerMonitor.getSchedulerInstance()
          .rescheduleJob(TriggerKey.triggerKey(oldTriggerName, oldTriggerGroup),
              QuartzConverters.getQuartzTrigger(trigger, oldTriggerName, oldTriggerGroup));
    } catch (SchedulerException | ParseException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param jobName {@inheritDoc}
   * @param jobGroup {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void triggerJob(String jobName, String jobGroup) throws QlackFuseSchedulerException {
    triggerJob(jobName, jobGroup, null);
  }

  /**
   * {@inheritDoc}
   *
   * @param jobName {@inheritDoc}
   * @param jobGroup {@inheritDoc}
   * @param dataMap {@inheritDoc}
   * @throws QlackFuseSchedulerException {@inheritDoc}
   */
  @Override
  public void triggerJob(String jobName, String jobGroup, Map<String, Object> dataMap)
      throws QlackFuseSchedulerException {
    try {
      if (dataMap != null) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(dataMap);
        SchedulerWrappedJob wrappedJob = getJobDetails(jobName, jobGroup, true, true);
        jobDataMap
            .put(Constants.QSCH_CLASS_NAME, wrappedJob.getDataMap().get(Constants.QSCH_CLASS_NAME));
        jobDataMap.put(Constants.QSCH_CLASS_ARRAY,
            wrappedJob.getDataMap().get(Constants.QSCH_CLASS_ARRAY));
        schedulerMonitor.getSchedulerInstance()
            .triggerJob(JobKey.jobKey(jobName, jobGroup), jobDataMap);
      } else {
        schedulerMonitor.getSchedulerInstance().triggerJob(JobKey.jobKey(jobName, jobGroup));
      }
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          ex.getLocalizedMessage());
    }
  }
}
