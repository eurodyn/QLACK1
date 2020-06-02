package com.eurodyn.qlack.fuse.modules.mailing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
    name = "mai_distribution_list"
)
public class MaiDistributionList implements Serializable {

  private String id;
  private String listName;
  private String description;
  private Long createdOn;
  private String createdBy;
  private Set<MaiNewsletter> maiNewsletters = new HashSet(0);
  private Set<MaiContact> maiContacts = new HashSet(0);

  public MaiDistributionList() {
  }

  public MaiDistributionList(String listName) {
    this.listName = listName;
  }

  public MaiDistributionList(String listName, String description, Long createdOn, String createdBy,
      Set<MaiNewsletter> maiNewsletters, Set<MaiContact> maiContacts) {
    this.listName = listName;
    this.description = description;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
    this.maiNewsletters = maiNewsletters;
    this.maiContacts = maiContacts;
  }

  @Id
  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(
      name = "list_name",
      nullable = false,
      length = 45
  )
  public String getListName() {
    return this.listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  @Column(
      name = "description",
      length = 45
  )
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(
      name = "created_on"
  )
  public Long getCreatedOn() {
    return this.createdOn;
  }

  public void setCreatedOn(Long createdOn) {
    this.createdOn = createdOn;
  }

  @Column(
      name = "created_by",
      length = 254
  )
  public String getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @ManyToMany(
      fetch = FetchType.LAZY
  )
  @JoinTable(
      name = "mai_newsletter_has_dl",
      joinColumns = {@JoinColumn(
          name = "dlist_id",
          nullable = false,
          updatable = false
      )},
      inverseJoinColumns = {@JoinColumn(
          name = "newsletter_id",
          nullable = false,
          updatable = false
      )}
  )
  public Set<MaiNewsletter> getMaiNewsletters() {
    return this.maiNewsletters;
  }

  public void setMaiNewsletters(Set<MaiNewsletter> maiNewsletters) {
    this.maiNewsletters = maiNewsletters;
  }

  @ManyToMany(
      fetch = FetchType.LAZY
  )
  @JoinTable(
      name = "mai_distr_list_has_contact",
      joinColumns = {@JoinColumn(
          name = "distribution_list_id",
          nullable = false,
          updatable = false
      )},
      inverseJoinColumns = {@JoinColumn(
          name = "contact_id",
          nullable = false,
          updatable = false
      )}
  )
  public Set<MaiContact> getMaiContacts() {
    return this.maiContacts;
  }

  public void setMaiContacts(Set<MaiContact> maiContacts) {
    this.maiContacts = maiContacts;
  }
}
