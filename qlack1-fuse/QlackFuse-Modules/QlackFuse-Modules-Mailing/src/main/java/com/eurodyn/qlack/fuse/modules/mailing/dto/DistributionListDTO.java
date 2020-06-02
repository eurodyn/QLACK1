package com.eurodyn.qlack.fuse.modules.mailing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data transfer object for distribution lists.
 *
 * @author European Dynamics SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistributionListDTO extends MailBaseDTO {

  private String name;

  private String description;

  private List<ContactDTO> contactList;

  private String createdBy;

  private Long createdOn;

  /**
   * Constructor with parameters
   *
   * @param name The name of the Distribution list
   * @param description The description of the Distribution List
   * @param contactList List of contacts in Distribution List
   */
  public DistributionListDTO(String name, String description, List<ContactDTO> contactList) {
    this.name = name;
    this.description = description;
    this.contactList = contactList;
  }

}
