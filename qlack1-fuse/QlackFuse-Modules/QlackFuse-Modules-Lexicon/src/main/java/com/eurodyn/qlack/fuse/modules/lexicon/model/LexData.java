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
public class LexData extends LexBase implements Serializable {

  private String id;
  private LexLanguage languageId;
  private LexKey keyId;
  private String value;
  private boolean approved;
  private String approvedBy;
  private Long approvedOn;

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
