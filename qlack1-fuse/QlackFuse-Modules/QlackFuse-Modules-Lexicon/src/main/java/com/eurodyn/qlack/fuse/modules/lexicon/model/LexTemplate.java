package com.eurodyn.qlack.fuse.modules.lexicon.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(
    name = "lex_template"
)
@Getter
@Setter
public class LexTemplate implements Serializable {

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

  @Column(
      name = "name",
      nullable = false
  )
  private String name;

  @Column(
      name = "value",
      length = 65535
  )
  private String value;

  @Column(
      name = "created_on",
      nullable = false
  )
  private long createdOn;

  @Column(
      name = "created_by",
      nullable = false,
      length = 36
  )
  private String createdBy;

  @Column(
      name = "last_modified_on"
  )
  private Long lastModifiedOn;

  @Column(
      name = "last_modified_by",
      length = 36
  )
  private String lastModifiedBy;

  public LexTemplate() {
  }

  public LexTemplate(LexLanguage languageId, String name, long createdOn, String createdBy) {
    this.languageId = languageId;
    this.name = name;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
  }

  public LexTemplate(LexLanguage languageId, String name, String value, long createdOn, String createdBy,
      Long lastModifiedOn, String lastModifiedBy) {
    this.languageId = languageId;
    this.name = name;
    this.value = value;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
    this.lastModifiedOn = lastModifiedOn;
    this.lastModifiedBy = lastModifiedBy;
  }

  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

}

