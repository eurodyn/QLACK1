package com.eurodyn.qlack.fuse.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Base class for QLACK DTO classes
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO extends AttributeDTO implements Serializable {

  private String id;

  //This user id is used to identify the user calling an action in order to use it when generating notification messages
  //or when this information should be stored along with the relevant item in the db. Please note that it *should not* be
  //used for security checks.
  private String srcUserId;

}
