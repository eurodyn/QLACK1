package com.eurodyn.qlack.fuse.commons.dto.mailing;

import java.io.Serializable;

/**
 * DTO class for QLACK EmailServerResponse class
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public class EmailServerResponseDTO implements Serializable {

  private String emailID;

  private String serverResponse;

  private long serverResponseDate;

  private boolean error;

  public EmailServerResponseDTO() {
    error = false;
  }

  public String getEmailID() {
    return emailID;
  }

  public void setEmailID(String emailID) {
    this.emailID = emailID;
  }

  public String getServerResponse() {
    return serverResponse;
  }

  public void setServerResponse(String serverResponse) {
    this.serverResponse = serverResponse;
  }

  public long getServerResponseDate() {
    return serverResponseDate;
  }

  public void setServerResponseDate(long serverResponseDate) {
    this.serverResponseDate = serverResponseDate;
  }

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }
}
