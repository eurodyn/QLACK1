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
    name = "mai_contact"
)
public class MaiContact implements Serializable {

  private String id;
  private String email;
  private String firstName;
  private String lastName;
  private String locale;
  private String userId;
  private Set<MaiDistributionList> maiDistributionLists = new HashSet(0);

  public MaiContact() {
  }

  public MaiContact(String email) {
    this.email = email;
  }

  public MaiContact(String email, String firstName, String lastName, String locale, String userId,
      Set<MaiDistributionList> maiDistributionLists) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.locale = locale;
    this.userId = userId;
    this.maiDistributionLists = maiDistributionLists;
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
      name = "email",
      nullable = false,
      length = 45
  )
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(
      name = "first_name",
      length = 254
  )
  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(
      name = "last_name",
      length = 254
  )
  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(
      name = "locale",
      length = 5
  )
  public String getLocale() {
    return this.locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  @Column(
      name = "user_id",
      length = 36
  )
  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @ManyToMany(
      fetch = FetchType.LAZY
  )
  @JoinTable(
      name = "mai_distr_list_has_contact",
      joinColumns = {@JoinColumn(
          name = "contact_id",
          nullable = false,
          updatable = false
      )},
      inverseJoinColumns = {@JoinColumn(
          name = "distribution_list_id",
          nullable = false,
          updatable = false
      )}
  )
  public Set<MaiDistributionList> getMaiDistributionLists() {
    return this.maiDistributionLists;
  }

  public void setMaiDistributionLists(Set<MaiDistributionList> maiDistributionLists) {
    this.maiDistributionLists = maiDistributionLists;
  }
}
