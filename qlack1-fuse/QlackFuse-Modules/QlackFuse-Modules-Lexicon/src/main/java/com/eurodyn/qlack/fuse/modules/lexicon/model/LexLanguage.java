package com.eurodyn.qlack.fuse.modules.lexicon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
    name = "lex_language"
)
public class LexLanguage extends LexBase implements Serializable {

  private String id;
  private String name;
  private String locale;
  private boolean active;
  private Set<LexTemplate> lexTemplates = new HashSet(0);
  private Set<LexData> lexDatas = new HashSet(0);

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
