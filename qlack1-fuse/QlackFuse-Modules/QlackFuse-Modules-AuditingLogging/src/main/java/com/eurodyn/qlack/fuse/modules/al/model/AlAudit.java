package com.eurodyn.qlack.fuse.modules.al.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(
    name = "al_audit"
)
public class AlAudit implements Serializable {

  private String id;
  private AlAuditLevel levelId;
  private AlAuditTrace traceId;
  private Long createdOn;
  private String event;
  private String groupName;
  private String prinSessionId;
  private String referenceId;
  private String shortDescription;

  public AlAudit() {
  }

  public AlAudit(String id, AlAuditLevel levelId, String event) {
    this.id = id;
    this.levelId = levelId;
    this.event = event;
  }

  public AlAudit(String id, AlAuditLevel levelId, AlAuditTrace traceId, Long createdOn, String event, String groupName,
      String prinSessionId, String referenceId, String shortDescription) {
    this.id = id;
    this.levelId = levelId;
    this.traceId = traceId;
    this.createdOn = createdOn;
    this.event = event;
    this.groupName = groupName;
    this.prinSessionId = prinSessionId;
    this.referenceId = referenceId;
    this.shortDescription = shortDescription;
  }

  @Id
  @Column(
      name = "id",
      unique = true,
      nullable = false
  )
  public String getId() {
    if (this.id == null) {
      this.id = java.util.UUID.randomUUID().toString();
    };
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "level_id",
      nullable = false
  )
  public AlAuditLevel getLevelId() {
    return this.levelId;
  }

  public void setLevelId(AlAuditLevel levelId) {
    this.levelId = levelId;
  }

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "trace_id"
  )
  public AlAuditTrace getTraceId() {
    return this.traceId;
  }

  public void setTraceId(AlAuditTrace traceId) {
    this.traceId = traceId;
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
      name = "event",
      nullable = false,
      length = 40
  )
  public String getEvent() {
    return this.event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  @Column(
      name = "group_name"
  )
  public String getGroupName() {
    return this.groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  @Column(
      name = "prin_session_id"
  )
  public String getPrinSessionId() {
    return this.prinSessionId;
  }

  public void setPrinSessionId(String prinSessionId) {
    this.prinSessionId = prinSessionId;
  }

  @Column(
      name = "reference_id",
      length = 36
  )
  public String getReferenceId() {
    return this.referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  @Column(
      name = "short_description"
  )
  public String getShortDescription() {
    return this.shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }
}
