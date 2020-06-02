package com.eurodyn.qlack.fuse.modules.al.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "al_audit_level",
    uniqueConstraints = {@UniqueConstraint(
        columnNames = {"name"}
    )}
)
public class AlAuditLevel implements Serializable {

  private String id;
  private Long createdOn;
  private String description;
  private String name;
  private String prinSessionId;
  private Set<AlAudit> alAudits = new HashSet(0);

  public AlAuditLevel() {
  }

  public AlAuditLevel(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public AlAuditLevel(String id, Long createdOn, String description, String name, String prinSessionId,
      Set<AlAudit> alAudits) {
    this.id = id;
    this.createdOn = createdOn;
    this.description = description;
    this.name = name;
    this.prinSessionId = prinSessionId;
    this.alAudits = alAudits;
  }

  @Id
  @Column(
      name = "id",
      unique = true,
      nullable = false
  )
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
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
      name = "name",
      unique = true,
      nullable = false,
      length = 10
  )
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(
      name = "prin_session_id",
      length = 40
  )
  public String getPrinSessionId() {
    return this.prinSessionId;
  }

  public void setPrinSessionId(String prinSessionId) {
    this.prinSessionId = prinSessionId;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "levelId"
  )
  public Set<AlAudit> getAlAudits() {
    return this.alAudits;
  }

  public void setAlAudits(Set<AlAudit> alAudits) {
    this.alAudits = alAudits;
  }
}
