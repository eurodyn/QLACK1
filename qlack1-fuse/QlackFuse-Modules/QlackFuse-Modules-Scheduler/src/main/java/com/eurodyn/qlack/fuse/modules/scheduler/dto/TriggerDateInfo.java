package com.eurodyn.qlack.fuse.modules.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author European Dynamics SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TriggerDateInfo implements Serializable {

  private Date endTime;

  private Date finalFireTime;

  private Date nextFireTime;

  private Date previousFireTime;

  private Date starTime;

}
