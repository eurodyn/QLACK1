package com.eurodyn.qlack.fuse.modules.lexicon.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;

/**
 * DTO class for entity LeyKey
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public class LexKeyDTO extends BaseDTO {

  private String groupId;

  private String name;

  private String createdBy;

  private long createdOn;

  private Long lastModifiedOn;

  private String lastModifiedBy;

  private LexDataDTO[] data;

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public LexDataDTO[] getData() {
    return data;
  }

  public void setData(LexDataDTO[] data) {
    this.data = data;
  }
}
