package com.eurodyn.qlack.fuse.modules.scheduler.service.impl;

import com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * Unit test class for SchedulerMonitorBean methods.
 *
 * @author European Dynamics SA
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SchedulerMonitorBeanTest {

  @InjectMocks
  private SchedulerMonitorBean schedulerMonitorBean;

  @Mock
  private Scheduler scheduler;

  @Test
  void startSchedulerTest() throws QlackFuseSchedulerException, NoSuchFieldException, SchedulerException {
    FieldSetter.setField(schedulerMonitorBean,
        schedulerMonitorBean.getClass().getDeclaredField("scheduler"),
        scheduler);
    schedulerMonitorBean.startScheduler();
    Mockito.verify(scheduler, Mockito.times(1)).start();
  }

  @Test
  void startSchedulerExceptionTest() throws NoSuchFieldException, SchedulerException {
    FieldSetter.setField(schedulerMonitorBean,
        schedulerMonitorBean.getClass().getDeclaredField("scheduler"),
        scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).start();
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerMonitorBean.startScheduler());
  }

  @Test
  void shutdownSchedulerTest() throws QlackFuseSchedulerException, NoSuchFieldException, SchedulerException {
    FieldSetter.setField(schedulerMonitorBean,
        schedulerMonitorBean.getClass().getDeclaredField("scheduler"),
        scheduler);
    schedulerMonitorBean.shutdownScheduler();
    Mockito.verify(scheduler, Mockito.times(1)).shutdown();
  }

  @Test
  void shutdownSchedulerExceptionTest() throws NoSuchFieldException, SchedulerException {
    FieldSetter.setField(schedulerMonitorBean,
        schedulerMonitorBean.getClass().getDeclaredField("scheduler"),
        scheduler);
    Mockito.doThrow(SchedulerException.class).when(scheduler).shutdown();
    Assertions.assertThrows(QlackFuseSchedulerException.class,
        () -> schedulerMonitorBean.shutdownScheduler());
  }

}
