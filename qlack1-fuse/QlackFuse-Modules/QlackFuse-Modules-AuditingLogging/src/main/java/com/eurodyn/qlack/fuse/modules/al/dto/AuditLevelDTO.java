package com.eurodyn.qlack.fuse.modules.al.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Audit level data transfer object
 *
 * @author European Dynamics SA
 */
public class AuditLevelDTO implements Serializable {

  private String name;
  private String id;
  private String description;
  private String prinSessionId;
  private Date createdOn;

  public AuditLevelDTO() {
  }

  public AuditLevelDTO(String name, String id, String description, String prinSessionId, Date createdOn) {
    this.name = name;
    this.id = id;
    this.description = description;
    this.prinSessionId = prinSessionId;
    this.createdOn = createdOn;
  }

  /**
   * parameterized Constructor
   */
  public AuditLevelDTO(String name) {
    this.setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPrinSessionId() {
    return prinSessionId;
  }

  public void setPrinSessionId(String prinSessionId) {
    this.prinSessionId = prinSessionId;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public enum DefaultLevels {
    trace, debug, info, warn, error, fatal
  }
}
