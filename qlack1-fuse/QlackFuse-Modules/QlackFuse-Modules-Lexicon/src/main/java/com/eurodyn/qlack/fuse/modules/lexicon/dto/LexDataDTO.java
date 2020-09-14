package com.eurodyn.qlack.fuse.modules.lexicon.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;

/**
 * DTO class for entity LexData
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public class LexDataDTO extends BaseDTO {

  private String locale;

  private String value;

  private String createdBy;

  private long createdOn;

  private Long lastModifiedOn;

  private String lastModifiedBy;

  private boolean approved;

  private String approvedBy;

  private long approvedOn;

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public long getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(long createdOn) {
    this.createdOn = createdOn;
  }

  public Long getLastModifiedOn() {
    return lastModifiedOn;
  }

  public void setLastModifiedOn(Long lastModifiedOn) {
    this.lastModifiedOn = lastModifiedOn;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public boolean isApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  public String getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(String approvedBy) {
    this.approvedBy = approvedBy;
  }

  public long getApprovedOn() {
    return approvedOn;
  }

  public void setApprovedOn(long approvedOn) {
    this.approvedOn = approvedOn;
  }
}
