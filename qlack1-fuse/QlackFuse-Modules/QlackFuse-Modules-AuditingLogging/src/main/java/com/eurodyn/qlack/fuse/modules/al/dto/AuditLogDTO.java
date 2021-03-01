package com.eurodyn.qlack.fuse.modules.al.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Audit log data transfer object
 *
 * @author European Dynamics SA
 */
public class AuditLogDTO implements Serializable {

  public static final Logger logger = Logger.getLogger(AuditLogDTO.class.getName());

  private String id;

  private String level;

  private Date createdOn;

  private String prinSessionId;

  private String shortDescription;

  private String event;

  private String traceData;

  private String referenceId;

  private String groupName;
  
  private String lang;


public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public String getPrinSessionId() {
    return prinSessionId;
  }

  public void setPrinSessionId(String prinSessionId) {
    this.prinSessionId = prinSessionId;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    if ((shortDescription != null) && (shortDescription.length() > 2048)) {
      logger.log(Level.INFO, "shortDescription value "
          + "was truncated to 2048 characters");
      shortDescription = shortDescription.substring(0, 2047);
    }
    this.shortDescription = shortDescription;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getTraceData() {
    return traceData;
  }

  public void setTraceData(String traceData) {
    this.traceData = traceData;
  }

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
  
  public String getLang() {
	return lang;
}

public void setLang(String lang) {
	this.lang = lang;
}

}
