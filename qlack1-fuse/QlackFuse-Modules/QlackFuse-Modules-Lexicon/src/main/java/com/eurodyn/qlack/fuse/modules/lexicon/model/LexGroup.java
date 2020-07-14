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
    name = "lex_group"
)
@Getter
@Setter
public class LexGroup implements Serializable {

  @Id
  private String id;

  @Column(
      name = "title"
  )
  private String title;

  @Column(
      name = "description",
      length = 65535
  )
  private String description;

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
      name = "last_modified_by",
      length = 36
  )
  private String lastModifiedBy;

  @Column(
      name = "last_modified_on"
  )
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

  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
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
