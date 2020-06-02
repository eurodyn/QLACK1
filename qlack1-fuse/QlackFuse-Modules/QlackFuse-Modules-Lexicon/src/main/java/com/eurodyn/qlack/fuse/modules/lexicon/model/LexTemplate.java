package com.eurodyn.qlack.fuse.modules.lexicon.model;


import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(
    name = "lex_template"
)
public class LexTemplate implements Serializable {
  private String id;
  private LexLanguage languageId;
  private String name;
  private String value;
  private long createdOn;
  private String createdBy;
  private Long lastModifiedOn;
  private String lastModifiedBy;

  public LexTemplate() {
  }

  public LexTemplate(LexLanguage languageId, String name, long createdOn, String createdBy) {
    this.languageId = languageId;
    this.name = name;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
  }

  public LexTemplate(LexLanguage languageId, String name, String value, long createdOn, String createdBy, Long lastModifiedOn, String lastModifiedBy) {
    this.languageId = languageId;
    this.name = name;
    this.value = value;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
    this.lastModifiedOn = lastModifiedOn;
    this.lastModifiedBy = lastModifiedBy;
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

  @Column(
      name = "name",
      nullable = false
  )
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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
}

