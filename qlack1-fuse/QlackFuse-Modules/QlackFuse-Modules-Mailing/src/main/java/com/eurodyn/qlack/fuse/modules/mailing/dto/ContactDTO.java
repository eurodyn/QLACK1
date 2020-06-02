package com.eurodyn.qlack.fuse.modules.mailing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Data transfer object for contacts.
 *
 * @author European Dynamics SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO implements Serializable {

  private String id;

  private String userID;

  private String firstName;

  private String lastName;

  private String email;

  private String locale;

}
