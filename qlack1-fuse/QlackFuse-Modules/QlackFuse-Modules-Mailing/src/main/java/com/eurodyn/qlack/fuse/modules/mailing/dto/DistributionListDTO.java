package com.eurodyn.qlack.fuse.modules.mailing.dto;

import java.util.List;

/**
 * Data transfer object for distribution lists.
 *
 * @author European Dynamics SA.
 */
public class DistributionListDTO extends MailBaseDTO {

  private String name;

  private String description;

  private List<ContactDTO> contactList;

  private String createdBy;

  private Long createdOn;

  public DistributionListDTO() {
  }

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ContactDTO> getContactList() {
    return contactList;
  }

  public void setContactList(List<ContactDTO> contactList) {
    this.contactList = contactList;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Long getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Long createdOn) {
    this.createdOn = createdOn;
  }
}
