package com.eurodyn.qlack.fuse.commons.dto;

/**
 * DTO class for entity User
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public class UserDTO extends BaseDTO {

  private String userID;

  private Long createdOn;

  private byte status;

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public Long getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Long createdOn) {
    this.createdOn = createdOn;
  }

  public byte getStatus() {
    return status;
  }

  public void setStatus(byte status) {
    this.status = status;
  }
}
