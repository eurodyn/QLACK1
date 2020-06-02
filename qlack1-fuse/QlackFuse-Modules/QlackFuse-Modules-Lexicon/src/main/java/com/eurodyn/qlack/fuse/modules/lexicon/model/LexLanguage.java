package com.eurodyn.qlack.fuse.modules.lexicon.model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
    name = "lex_language"
)
public class LexLanguage implements Serializable {
  private String id;
  private String name;
  private String locale;
  private long createdOn;
  private String createdBy;
  private Long lastModifiedOn;
  private String lastModifiedBy;
  private boolean active;
  private Set<LexTemplate> lexTemplates = new HashSet(0);
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

  public LexLanguage(String name, String locale, long createdOn, String createdBy, Long lastModifiedOn, String lastModifiedBy, boolean active, Set<LexTemplate> lexTemplates, Set<LexData> lexDatas) {
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

  @Column(
      name = "name",
      nullable = false,
      length = 64
  )
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(
      name = "locale",
      nullable = false,
      length = 5
  )
  public String getLocale() {
    return this.locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
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

  @Column(
      name = "active",
      nullable = false
  )
  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "languageId"
  )
  public Set<LexTemplate> getLexTemplates() {
    return this.lexTemplates;
  }

  public void setLexTemplates(Set<LexTemplate> lexTemplates) {
    this.lexTemplates = lexTemplates;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "languageId"
  )
  public Set<LexData> getLexDatas() {
    return this.lexDatas;
  }

  public void setLexDatas(Set<LexData> lexDatas) {
    this.lexDatas = lexDatas;
  }
}
