package com.eurodyn.qlack.fuse.modules.lexicon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(
    name = "lex_data",
    uniqueConstraints = {@UniqueConstraint(
        columnNames = {"key_id", "language_id"}
    )}
)
public class LexData implements Serializable {

  private String id;
  private LexLanguage languageId;
  private LexKey keyId;
  private String value;
  private String createdBy;
  private long createdOn;
  private Long lastModifiedOn;
  private String lastModifiedBy;
  private boolean approved;
  private String approvedBy;
  private Long approvedOn;

  public LexData() {
  }

  public LexData(LexLanguage languageId, LexKey keyId, String createdBy, long createdOn, boolean approved) {
    this.languageId = languageId;
    this.keyId = keyId;
    this.createdBy = createdBy;
    this.createdOn = createdOn;
    this.approved = approved;
  }

  public LexData(LexLanguage languageId, LexKey keyId, String value, String createdBy, long createdOn,
      Long lastModifiedOn, String lastModifiedBy, boolean approved, String approvedBy, Long approvedOn) {
    this.languageId = languageId;
    this.keyId = keyId;
    this.value = value;
    this.createdBy = createdBy;
    this.createdOn = createdOn;
    this.lastModifiedOn = lastModifiedOn;
    this.lastModifiedBy = lastModifiedBy;
    this.approved = approved;
    this.approvedBy = approvedBy;
    this.approvedOn = approvedOn;
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

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "language_id",
      nullable = false
  )
  public LexLanguage getLanguageId() {
    return this.languageId;
  }

  public void setLanguageId(LexLanguage languageId) {
    this.languageId = languageId;
  }

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "key_id",
      nullable = false
  )
  public LexKey getKeyId() {
    return this.keyId;
  }

  public void setKeyId(LexKey keyId) {
    this.keyId = keyId;
  }

  @Column(
      name = "value",
      length = 65535
  )
  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Column(
      name = "created_by",
      nullable = false,
      length = 36
  )
  public String getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @Column(
      name = "created_on",
      nullable = false
  )
  public long getCreatedOn() {
    return this.createdOn;
  }

  public void setCreatedOn(long createdOn) {
    this.createdOn = createdOn;
  }

  @Column(
      name = "last_modified_on"
  )
  public Long getLastModifiedOn() {
    return this.lastModifiedOn;
  }

  public void setLastModifiedOn(Long lastModifiedOn) {
    this.lastModifiedOn = lastModifiedOn;
  }

  @Column(
      name = "last_modified_by",
      length = 36
  )
  public String getLastModifiedBy() {
    return this.lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  @Column(
      name = "approved",
      nullable = false
  )
  public boolean isApproved() {
    return this.approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  @Column(
      name = "approved_by",
      length = 36
  )
  public String getApprovedBy() {
    return this.approvedBy;
  }

  public void setApprovedBy(String approvedBy) {
    this.approvedBy = approvedBy;
  }

  @Column(
      name = "approved_on"
  )
  public Long getApprovedOn() {
    return this.approvedOn;
  }

  public void setApprovedOn(Long approvedOn) {
    this.approvedOn = approvedOn;
  }
}
