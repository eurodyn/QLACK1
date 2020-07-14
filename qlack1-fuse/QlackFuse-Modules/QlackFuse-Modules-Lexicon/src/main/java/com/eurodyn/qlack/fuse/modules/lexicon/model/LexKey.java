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
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class LexKey implements Serializable {

  @Id
  private String id;

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "group_id"
  )
  private LexGroup groupId;

  @Column(
      name = "name",
      unique = true
  )
  private String name;

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

  private Set<LexData> lexDatas = new HashSet(0);

  public LexKey() {
  }

  public LexKey(String createdBy, long createdOn) {
    this.createdBy = createdBy;
    this.createdOn = createdOn;
  }

  public LexKey(LexGroup groupId, String name, String createdBy, long createdOn, Long lastModifiedOn,
      String lastModifiedBy, Set<LexData> lexDatas) {
    this.groupId = groupId;
    this.name = name;
    this.createdBy = createdBy;
    this.createdOn = createdOn;
    this.lastModifiedOn = lastModifiedOn;
    this.lastModifiedBy = lastModifiedBy;
    this.lexDatas = lexDatas;
  }

  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
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
