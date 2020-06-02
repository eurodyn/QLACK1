package com.eurodyn.qlack.fuse.commons.dto.mailing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO class for QLACK EmailServerResponse class
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@AllArgsConstructor
public class EmailServerResponseDTO implements Serializable {

  private String emailID;

  private String serverResponse;

  private long serverResponseDate;

  private boolean error;

  public EmailServerResponseDTO() {
    error = false;
  }

}
