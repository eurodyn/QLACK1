package com.eurodyn.qlack.fuse.modules.lexicon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
    name = "lex_key",
    uniqueConstraints = {@UniqueConstraint(
        columnNames = {"name"}
    )}
)
public class LexKey extends LexBase implements Serializable {

  private String id;
  private LexGroup groupId;
  private String name;
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

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "group_id"
  )
  public LexGroup getGroupId() {
    return this.groupId;
  }

  public void setGroupId(LexGroup groupId) {
    this.groupId = groupId;
  }

  @Column(
      name = "name",
      unique = true
  )
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "keyId"
  )
  public Set<LexData> getLexDatas() {
    return this.lexDatas;
  }

  public void setLexDatas(Set<LexData> lexDatas) {
    this.lexDatas = lexDatas;
  }
}
