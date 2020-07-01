package com.eurodyn.qlack.fuse.modules.scheduler.service.impl;

import com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA.
 */
@Singleton
@Startup
public class SchedulerMonitorBean {

  private static final Logger logger = Logger.getLogger(SchedulerMonitorBean.class.getName());

  // the scheduler
  private static Scheduler scheduler;

  /**
   * Get the Scheduler instance.
   *
   * @return Scheduler
   */
  protected Scheduler getSchedulerInstance() {
    return scheduler;
  }

  /**
   * Initialize the Scheduler instance.
   */
  private void initScheduler() throws SchedulerException {
    logger.log(Level.CONFIG, "Initialising default scheduler...");
    scheduler = StdSchedulerFactory.getDefaultScheduler();
    logger.log(Level.CONFIG, "Default scheduler initialised.");
  }

  /**
   * Start the Scheduler instance.
   */
  @PostConstruct
  public void startScheduler() throws QlackFuseSchedulerException {
    try {
      logger.log(Level.CONFIG, "Starting scheduler...");
      if (scheduler == null) {
        initScheduler();
      }
      scheduler.start();
      logger.log(Level.CONFIG, "Scheduler started [{0}].", scheduler);
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          "Could not start the Scheduler");
    }
  }

  /**
   * Shutdown the Scheduler instance.
   */
  @PreDestroy
  public void shutdownScheduler() throws QlackFuseSchedulerException {
    try {
      logger.log(Level.CONFIG, "Shutting-down scheduler...");
      if (scheduler != null) {
        scheduler.shutdown();
      }
      logger.log(Level.CONFIG, "Scheduler shutdown.");
    } catch (SchedulerException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0001,
          "Could not shutdown the Scheduler");
    }
  }

}
