package com.eurodyn.qlack.fuse.modules.scheduler.builder;

import com.eurodyn.qlack.fuse.modules.scheduler.exception.QlackFuseSchedulerException;
import com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerJob;
import com.eurodyn.qlack.fuse.modules.scheduler.job.SchedulerWrappedJob;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 * This is the scheduler builder class for a job.
 *
 * @author European Dynamics SA.
 */
public class SchedulerJobBuilder {

  private static final Logger logger = Logger.getLogger(SchedulerJobBuilder.class.getName());

  //the job class
  private Class<? extends SchedulerJob> jobClass;

  //the job name
  private String jobName;

  //the job group
  private String jobGroup;

  //the job data
  private Map<String, Object> dataMap;

  //boolean if the job is durable
  private boolean durable = false;

  /**
   * default constructor for the scheduler job builder
   */
  private SchedulerJobBuilder() {

  }

  /**
   * Create new job.
   */
  public static SchedulerJobBuilder newJob() {
    return new SchedulerJobBuilder();
  }

  /**
   * Get the job class.
   */
  private byte[] getJobClass() throws QlackFuseSchedulerException {
    byte[] retVal;

    try {
      InputStream u = Thread.currentThread().getContextClassLoader().getResourceAsStream(
          jobClass.getName().replaceAll("\\.", Matcher.quoteReplacement(File.separator))
              + ".class");
      retVal = IOUtils.toByteArray(u);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0004,
          ex.getLocalizedMessage());
    }

    return retVal;
  }

  /**
   * Build the new job.
   */
  public SchedulerWrappedJob build() throws QlackFuseSchedulerException {
    checkJobData();

    SchedulerWrappedJob swj = new SchedulerWrappedJob();
    swj.setJobClassName(jobClass.getName());
    swj.setJobClass(getJobClass());
    if (StringUtils.isEmpty(jobName)) {
      swj.setJobName("Job_" + UUID.randomUUID().toString());
    } else {
      swj.setJobName(jobName);
    }
    if (StringUtils.isEmpty(jobGroup)) {
      swj.setJobGroup("JobGroup_" + UUID.randomUUID().toString());
    } else {
      swj.setJobGroup(jobGroup);
    }
    if (dataMap != null) {
      swj.setDataMap(dataMap);
    }
    swj.setDurable(durable);

    return swj;
  }

  /**
   * Set the job class.
   *
   * @param jobClass - the job class parameter passed
   */
  public SchedulerJobBuilder withJobClass(Class<? extends SchedulerJob> jobClass) {
    this.jobClass = jobClass;
    return this;
  }

  /**
   * Call to set the durable parameter of the new job to true.
   */
  public SchedulerJobBuilder durable() {
    this.durable = true;
    return this;
  }

  /**
   * Set the job data.
   *
   * @param dataMap - a Map with the data for the job
   */
  public SchedulerJobBuilder withData(Map<String, Object> dataMap) {
    this.dataMap = dataMap;

    return this;
  }

  /**
   * Set the job identity (job name and job group).
   *
   * @param jobName - the job name parameter passed
   * @param jobGroup - the job group parameter passed
   */
  public SchedulerJobBuilder withIdentity(String jobName, String jobGroup) {
    this.jobName = jobName;
    this.jobGroup = jobGroup;

    return this;
  }

  /**
   * Checks weather the job class is null.
   */
  private void checkJobData() throws QlackFuseSchedulerException {
    if (jobClass == null) {
      throw new QlackFuseSchedulerException(QlackFuseSchedulerException.CODES.ERR_SCH_0003,
          "Job class can not be empty.");
    }
  }
}
