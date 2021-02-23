package com.eurodyn.qlack.fuse.modules.al.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(
    name = "al_audit_trace"
)
public class AlAuditTrace implements Serializable {

  @EmbeddedId
  private Pk id;
  private String traceData;

  public AlAuditTrace() {
  }

  public AlAuditTrace(Pk id, String traceData) {
    this.id = id;
    this.traceData = traceData;
  }

  public AlAuditTrace(Pk id, String traceData, AlAudit alAudit) {
    this.id = id;
    this.traceData = traceData;
    this.alAudit = alAudit;
  }

  public Pk getId() {
    return id;
  }

  public void setId(Pk id) {
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

  @ManyToOne
  @JoinColumn(name="id", insertable = false, updatable = false)
  private AlAudit alAudit;

  public AlAudit getAlAudit() {
    return alAudit;
  }

  public void setAlAudit(AlAudit alAudit) {
    this.alAudit = alAudit;
  }
}
