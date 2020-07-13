package com.eurodyn.qlack.fuse.modules.scheduler.job;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The scheduler wrapped job.
 *
 * @author European Dynamics SA.
 */
public class SchedulerWrappedJob implements Serializable {

  //the job class
  private byte[] jobClass;

  //the  job class name
  private String jobClassName;

  //the job name
  private String jobName;

  // the job group
  private String jobGroup;

  // the job data
  private transient Map<String, Object> dataMap;

  // weather the job is durable (default is false)
  private boolean durable = false;

  /**
   * Get the job class.
   *
   * @return the jobClass
   */
  public byte[] getJobClass() {
    return jobClass.clone();
  }

  /**
   * Set the job class.
   *
   * @param jobClass the jobClass to set
   */
  public void setJobClass(byte[] jobClass) {
    this.jobClass = jobClass.clone();
  }

  /**
   * Get the job name.
   *
   * @return the jobName
   */
  public String getJobName() {
    return jobName;
  }

  /**
   * Set the job name.
   *
   * @param jobName the jobName to set
   */
  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  /**
   * Get the job group.
   *
   * @return the jobGroup
   */
  public String getJobGroup() {
    return jobGroup;
  }

  /**
   * Set the job group.
   *
   * @param jobGroup the jobGroup to set
   */
  public void setJobGroup(String jobGroup) {
    this.jobGroup = jobGroup;
  }

  /**
   * Get the job data.
   *
   * @return the dataMap
   */
  public Map<String, Object> getDataMap() {
    return dataMap != null
        ? new HashMap<>(dataMap)
        : null;
  }

  /**
   * Set the job data.
   *
   * @param dataMap the dataMap to set
   */
  public void setDataMap(Map<String, Object> dataMap) {
    this.dataMap = new HashMap<>(dataMap);
  }

  /**
   * Get the job class name.
   *
   * @return the jobClassName
   */
  public String getJobClassName() {
    return jobClassName;
  }

  /**
   * Set the job class name.
   *
   * @param jobClassName the jobClassName to set
   */
  public void setJobClassName(String jobClassName) {
    this.jobClassName = jobClassName;
  }

  /**
   * Get weather the job is durable.
   *
   * @return the durable
   */
  public boolean isDurable() {
    return durable;
  }

  /**
   * Set weather the job is durable.
   *
   * @param durable the durable to set
   */
  public void setDurable(boolean durable) {
    this.durable = durable;
  }

}
