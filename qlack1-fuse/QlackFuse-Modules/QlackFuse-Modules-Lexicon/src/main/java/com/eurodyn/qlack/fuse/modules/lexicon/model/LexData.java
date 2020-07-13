package com.eurodyn.qlack.fuse.modules.lexicon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(
    name = "lex_data",
    uniqueConstraints = {@UniqueConstraint(
        columnNames = {"key_id", "language_id"}
    )}
)
@Getter
@Setter
public class LexData implements Serializable {

  @Id
  private String id;

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "language_id",
      nullable = false
  )
  private LexLanguage languageId;

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "key_id",
      nullable = false
  )
  private LexKey keyId;

  @Column(
      name = "value",
      length = 65535
  )
  private String value;

  @Column(
      name = "created_by",
      nullable = false,
      length = 36
  )
  private String createdBy;

  @Column(
      name = "created_on",
      nullable = false
  )
  private long createdOn;

  @Column(
      name = "last_modified_on"
  )
  private Long lastModifiedOn;

  @Column(
      name = "last_modified_by",
      length = 36
  )
  private String lastModifiedBy;

  @Column(
      name = "approved",
      nullable = false
  )
  private boolean approved;

  @Column(
      name = "approved_by",
      length = 36
  )
  private String approvedBy;

  @Column(
      name = "approved_on"
  )
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

  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

}
