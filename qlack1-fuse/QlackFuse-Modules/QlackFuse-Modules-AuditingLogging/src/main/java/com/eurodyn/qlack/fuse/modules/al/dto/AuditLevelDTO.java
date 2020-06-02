package com.eurodyn.qlack.fuse.modules.al.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Audit level data transfer object
 *
 * @author European Dynamics SA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLevelDTO implements Serializable {

  private String name;
  private String id;
  private String description;
  private String prinSessionId;
  private Date createdOn;

  /**
   * parameterized Constructor
   */
  public AuditLevelDTO(String name) {
    this.setName(name);
  }

  public enum DefaultLevels {
    trace, debug, info, warn, error, fatal
  }

}
