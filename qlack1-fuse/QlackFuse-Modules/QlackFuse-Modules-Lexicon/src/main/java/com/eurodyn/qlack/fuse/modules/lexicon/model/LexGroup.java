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
    name = "lex_group"
)
public class LexGroup implements Serializable {

  private String id;
  private String title;
  private String description;
  private long createdOn;
  private String createdBy;
  private String lastModifiedBy;
  private Long lastModifiedOn;
  private Set<LexKey> lexKeies = new HashSet(0);

  public LexGroup() {
  }

  public LexGroup(long createdOn, String createdBy) {
    this.createdOn = createdOn;
    this.createdBy = createdBy;
  }

  public LexGroup(String title, String description, long createdOn, String createdBy, String lastModifiedBy,
      Long lastModifiedOn, Set<LexKey> lexKeies) {
    this.title = title;
    this.description = description;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
    this.lastModifiedBy = lastModifiedBy;
    this.lastModifiedOn = lastModifiedOn;
    this.lexKeies = lexKeies;
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
      name = "title"
  )
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Column(
      name = "description",
      length = 65535
  )
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
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
      name = "last_modified_on"
  )
  public Long getLastModifiedOn() {
    return this.lastModifiedOn;
  }

  public void setLastModifiedOn(Long lastModifiedOn) {
    this.lastModifiedOn = lastModifiedOn;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "groupId"
  )
  public Set<LexKey> getLexKeies() {
    return this.lexKeies;
  }

  public void setLexKeies(Set<LexKey> lexKeies) {
    this.lexKeies = lexKeies;
  }
}
