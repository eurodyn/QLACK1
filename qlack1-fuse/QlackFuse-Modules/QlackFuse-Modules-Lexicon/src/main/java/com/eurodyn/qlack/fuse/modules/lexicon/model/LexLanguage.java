package com.eurodyn.qlack.fuse.modules.lexicon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
    name = "lex_language"
)
@Getter
@Setter
public class LexLanguage implements Serializable {

  @Id
  private String id;

  @Column(
      name = "name",
      nullable = false,
      length = 64
  )
  private String name;

  @Column(
      name = "locale",
      nullable = false,
      length = 5
  )
  private String locale;

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

  @Column(
      name = "active",
      nullable = false
  )
  private boolean active;

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "languageId"
  )
  private Set<LexTemplate> lexTemplates = new HashSet(0);

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "languageId"
  )
  private Set<LexData> lexDatas = new HashSet(0);

  public LexLanguage() {
  }

  public LexLanguage(String name, String locale, long createdOn, String createdBy, boolean active) {
    this.name = name;
    this.locale = locale;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
    this.active = active;
  }

  public LexLanguage(String name, String locale, long createdOn, String createdBy, Long lastModifiedOn,
      String lastModifiedBy, boolean active, Set<LexTemplate> lexTemplates, Set<LexData> lexDatas) {
    this.name = name;
    this.locale = locale;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
    this.lastModifiedOn = lastModifiedOn;
    this.lastModifiedBy = lastModifiedBy;
    this.active = active;
    this.lexTemplates = lexTemplates;
    this.lexDatas = lexDatas;
  }

  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

}
