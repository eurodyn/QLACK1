package com.eurodyn.qlack.fuse.modules.scheduler.service.impl;

import com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;
import com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerWrappedJob;
import com.eurodyn.qlack.fuse.modules.scheduler.job.trigger.SchedulerWrappedTrigger;
import com.eurodyn.qlack.fuse.modules.scheduler.util.Constants.TRIGGERS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.Date;

/**
 * Unit test class for SchedulerManagerBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class SchedulerManagerBeanTest {

  private static final String jobName = "Test Job Name";
  @InjectMocks
  private SchedulerManagerBean schedulerManagerBean;
  @Mock
  private SchedulerMonitorBean schedulerMonitor;
  @Mock
  private SchedulerWrappedJob schedulerWrappedJob;
  @Mock
  private SchedulerWrappedTrigger schedulerWrappedTrigger;
  @Mock
  private Scheduler scheduler;
  @Mock
  private Trigger trigger;

  @Test
  void scheduleJobTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerWrappedJob.getJobName()).thenReturn(jobName);
    Mockito.when(schedulerWrappedTrigger.getTriggerName()).thenReturn(jobName);
    Mockito.when(schedulerWrappedTrigger.getTriggerType()).thenReturn(TRIGGERS.ASAP);
    Mockito.when(schedulerWrappedTrigger.getStartOn()).thenReturn(new Date());
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.scheduleJob(schedulerWrappedJob, schedulerWrappedTrigger);
    Mockito.verify(scheduler, Mockito.times(1)).scheduleJob(Mockito.any(), Mockito.any());
  }

  @Test
  void scheduleJobExceptionTest() throws SchedulerException {
    Mockito.when(schedulerWrappedJob.getJobName()).thenReturn(jobName);
    Mockito.when(schedulerWrappedTrigger.getTriggerName()).thenReturn(jobName);
    Mockito.when(schedulerWrappedTrigger.getTriggerType()).thenReturn(TRIGGERS.ASAP);
    Mockito.when(schedulerWrappedTrigger.getStartOn()).thenReturn(new Date());
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).scheduleJob(Mockito.any(), Mockito.any());
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerManagerBean.scheduleJob(schedulerWrappedJob, schedulerWrappedTrigger));
  }

  @Test
  void scheduleJobIfDifferentTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerWrappedJob.getJobName()).thenReturn(jobName);
    Mockito.when(schedulerWrappedTrigger.getTriggerName()).thenReturn(jobName);
    Mockito.when(schedulerWrappedTrigger.getTriggerType()).thenReturn(TRIGGERS.ASAP);
    Mockito.when(schedulerWrappedTrigger.getStartOn()).thenReturn(new Date());
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.scheduleJobIfDifferent(schedulerWrappedJob, schedulerWrappedTrigger);
    Mockito.verify(scheduler, Mockito.times(1)).getJobDetail(Mockito.any());
  }

  @Test
  void scheduleJobIfDifferentExceptionTest() throws SchedulerException {
    Mockito.when(schedulerWrappedJob.getJobName()).thenReturn(jobName);
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).getJobDetail(Mockito.any());
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerManagerBean.scheduleJobIfDifferent(schedulerWrappedJob, schedulerWrappedTrigger));
  }

  @Test
  void clearTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.clear();
    Mockito.verify(scheduler, Mockito.times(1)).clear();
  }

  @Test
  void clearExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).clear();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.clear());
  }

  @Test
  void pauseAllTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.pauseAll();
    Mockito.verify(scheduler, Mockito.times(1)).pauseAll();
  }

  @Test
  void pauseAllExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).pauseAll();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.pauseAll());
  }

  @Test
  void isShutDownTest() throws SchedulerException, QlackFuseSchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(scheduler.isShutdown()).thenReturn(true);
    Assertions.assertTrue(schedulerManagerBean.isShutdown());
  }

  @Test
  void isShutdownExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).isShutdown();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.isShutdown());
  }

  @Test
  void isStartedTest() throws SchedulerException, QlackFuseSchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(scheduler.isStarted()).thenReturn(true);
    Assertions.assertTrue(schedulerManagerBean.isStarted());
  }

  @Test
  void isStartedExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).isStarted();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.isStarted());
  }

  @Test
  void isInStandbyModeTest() throws SchedulerException, QlackFuseSchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(scheduler.isInStandbyMode()).thenReturn(true);
    Assertions.assertTrue(schedulerManagerBean.isInStandbyMode());
  }

  @Test
  void isInStandbyModeExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).isInStandbyMode();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.isInStandbyMode());
  }

  @Test
  void startTest() throws QlackFuseSchedulerException {
    schedulerManagerBean.start();
    Mockito.verify(schedulerMonitor, Mockito.times(1)).startScheduler();
  }

  @Test
  void stopTest() throws QlackFuseSchedulerException {
    schedulerManagerBean.stop();
    Mockito.verify(schedulerMonitor, Mockito.times(1)).shutdownScheduler();
  }

  @Test
  void standbyTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.standby();
    Mockito.verify(scheduler, Mockito.times(1)).standby();
  }

  @Test
  void standbyExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).standby();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.standby());
  }

  @Test
  void resumeAllTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.resumeAll();
    Mockito.verify(scheduler, Mockito.times(1)).resumeAll();
  }

  @Test
  void resumeAllExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).resumeAll();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.resumeAll());
  }

  @Test
  void deleteJobTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.deleteJob(jobName, jobName);
    Mockito.verify(scheduler, Mockito.times(1)).deleteJob(Mockito.any());
  }

  @Test
  void deleteJobExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).deleteJob(Mockito.any());
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.deleteJob(jobName, jobName));
  }

  @Test
  void getSchedulerInstanceIDTest() throws SchedulerException, QlackFuseSchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(scheduler.getSchedulerInstanceId()).thenReturn(jobName);
    Assertions.assertEquals(jobName, schedulerManagerBean.getSchedulerInstanceID());
  }

  @Test
  void getSchedulerInstanceIDExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).getSchedulerInstanceId();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.getSchedulerInstanceID());
  }

  @Test
  void getSchedulerNameTest() throws SchedulerException, QlackFuseSchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(scheduler.getSchedulerName()).thenReturn(jobName);
    Assertions.assertEquals(jobName, schedulerManagerBean.getSchedulerName());
  }

  @Test
  void getSchedulerNameExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).getSchedulerName();
    Assertions.assertThrows(QlackFuseSchedulerException.class, () -> schedulerManagerBean.getSchedulerName());
  }

  @Test
  void getNextFiretimeForTriggerTest() throws QlackFuseSchedulerException, SchedulerException {
    Date now = new Date();
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(scheduler.getTrigger(new TriggerKey(jobName, jobName))).thenReturn(trigger);
    Mockito.when(trigger.getNextFireTime()).thenReturn(now);
    Assertions.assertEquals(now, schedulerManagerBean.getNextFiretimeForTrigger(jobName, jobName));
  }

  @Test
  void getNextFiretimeForTriggerExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).getTrigger(new TriggerKey(jobName, jobName));
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerManagerBean.getNextFiretimeForTrigger(jobName, jobName));
  }

  @Test
  void getDateInfoForTriggerTest() throws QlackFuseSchedulerException, SchedulerException {
    Date now = new Date();
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(scheduler.getTrigger(new TriggerKey(jobName, jobName))).thenReturn(trigger);
    Mockito.when(trigger.getNextFireTime()).thenReturn(now);
    Assertions.assertEquals(now, schedulerManagerBean.getDateInfoForTrigger(jobName, jobName).getNextFireTime());
  }

  @Test
  void getDateInfoForTriggerExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).getTrigger(new TriggerKey(jobName, jobName));
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerManagerBean.getDateInfoForTrigger(jobName, jobName));
  }

  @Test
  void pauseJobTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.pauseJob(jobName, jobName);
    Mockito.verify(scheduler, Mockito.times(1)).pauseJob(Mockito.any());
  }

  @Test
  void pauseJobExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).pauseJob(Mockito.any());
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerManagerBean.pauseJob(jobName, jobName));
  }

  @Test
  void pauseTriggerTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.pauseTrigger(jobName, jobName);
    Mockito.verify(scheduler, Mockito.times(1)).pauseTrigger(Mockito.any());
  }

  @Test
  void pauseTriggerExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).pauseTrigger(Mockito.any());
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerManagerBean.pauseTrigger(jobName, jobName));
  }

  @Test
  void rescheduleJobTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(schedulerWrappedTrigger.getTriggerName()).thenReturn(jobName);
    Mockito.when(schedulerWrappedTrigger.getTriggerType()).thenReturn(TRIGGERS.ASAP);
    Mockito.when(schedulerWrappedTrigger.getStartOn()).thenReturn(new Date());
    schedulerManagerBean.rescheduleJob(jobName, jobName, schedulerWrappedTrigger);
    Mockito.verify(scheduler, Mockito.times(1)).rescheduleJob(Mockito.any(), Mockito.any());
  }

  @Test
  void rescheduleJobExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.when(schedulerWrappedTrigger.getTriggerName()).thenReturn(jobName);
    Mockito.when(schedulerWrappedTrigger.getTriggerType()).thenReturn(TRIGGERS.ASAP);
    Mockito.when(schedulerWrappedTrigger.getStartOn()).thenReturn(new Date());
    Mockito.doThrow(SchedulerException.class).when(scheduler).rescheduleJob(Mockito.any(), Mockito.any());
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerManagerBean.rescheduleJob(jobName, jobName, schedulerWrappedTrigger));
  }

  @Test
  void triggerJobTest() throws QlackFuseSchedulerException, SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    schedulerManagerBean.triggerJob(jobName, jobName);
    Mockito.verify(scheduler, Mockito.times(1)).triggerJob(Mockito.any());
  }

  @Test
  void triggerJobExceptionTest() throws SchedulerException {
    Mockito.when(schedulerMonitor.getSchedulerInstance()).thenReturn(scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).triggerJob(Mockito.any());
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerManagerBean.triggerJob(jobName, jobName));
  }


}
