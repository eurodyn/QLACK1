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
public class LexGroup extends LexBase implements Serializable {

  private String id;
  private String title;
  private String description;
  private Set<LexKey> lexKeies = new HashSet(0);

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
