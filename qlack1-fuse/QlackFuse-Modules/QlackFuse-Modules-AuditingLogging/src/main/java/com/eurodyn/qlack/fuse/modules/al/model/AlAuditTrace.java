package com.eurodyn.qlack.fuse.modules.al.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "al_audit_trace"
)
public class AlAuditTrace implements Serializable {

  private String id;
  private String traceData;
  private Set<AlAudit> alAudits = new HashSet(0);

  public AlAuditTrace() {
  }

  public AlAuditTrace(String id, String traceData) {
    this.id = id;
    this.traceData = traceData;
  }

  public AlAuditTrace(String id, String traceData, Set<AlAudit> alAudits) {
    this.id = id;
    this.traceData = traceData;
    this.alAudits = alAudits;
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
    }
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(
      name = "trace_data",
      nullable = false
  )
  public String getTraceData() {
    return this.traceData;
  }

  public void setTraceData(String traceData) {
    this.traceData = traceData;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "traceId"
  )
  public Set<AlAudit> getAlAudits() {
    return this.alAudits;
  }

  public void setAlAudits(Set<AlAudit> alAudits) {
    this.alAudits = alAudits;
  }
}
