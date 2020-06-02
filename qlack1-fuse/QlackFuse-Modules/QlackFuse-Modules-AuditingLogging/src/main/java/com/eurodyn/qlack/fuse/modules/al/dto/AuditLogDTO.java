package com.eurodyn.qlack.fuse.modules.al.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

/**
 * Audit log data transfer object
 *
 * @author European Dynamics SA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AuditLogDTO implements Serializable {

  private String id;

  private String level;

  private Date createdOn;

  private String prinSessionId;

  private String shortDescription;

  private String event;

  private String traceData;

  private String referenceId;

  private String groupName;

  public void setShortDescription(String shortDescription) {
    if ((shortDescription != null) && (shortDescription.length() > 2048)) {
      log.info("shortDescription value "
          + "was truncated to 2048 characters");
      shortDescription = shortDescription.substring(0, 2047);
    }
    this.shortDescription = shortDescription;
  }

}
