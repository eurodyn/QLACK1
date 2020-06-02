package com.eurodyn.qlack.fuse.modules.scheduler.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics S.A.
 */
public class SchedulerJobRunner implements Job {

  private static final Logger logger = Logger.getLogger(SchedulerJobRunner.class.getName());

  /**
   * Scheduler job runner start execution.
   */
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      SchedulerJobClassLoader qcl = new SchedulerJobClassLoader(
          (byte[]) context.getMergedJobDataMap().get(Constants.QSCH_CLASS_ARRAY),
          (String) context.getMergedJobDataMap().get(Constants.QSCH_CLASS_NAME));
      Class jobClass = qcl
          .loadClass((String) context.getMergedJobDataMap().get(Constants.QSCH_CLASS_NAME));
      Method method = jobClass.getMethod("execute", Map.class);
      method.invoke(jobClass.newInstance(), context.getMergedJobDataMap());
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException |
        SecurityException | IllegalArgumentException | InvocationTargetException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
    }
  }

}
