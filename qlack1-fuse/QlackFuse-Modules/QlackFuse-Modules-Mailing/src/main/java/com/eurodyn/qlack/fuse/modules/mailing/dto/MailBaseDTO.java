package com.eurodyn.qlack.fuse.modules.mailing.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;

/**
 * Base Data transfer object for Mailing.
 *
 * @author European Dynamics SA.
 */
public class MailBaseDTO extends BaseDTO {

  private String id;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }
}
