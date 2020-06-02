package com.eurodyn.qlack.fuse.modules.lexicon.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "lex_key",
    uniqueConstraints = {@UniqueConstraint(
        columnNames = {"name"}
    )}
)
public class LexKey implements Serializable {
  private String id;
  private LexGroup groupId;
  private String name;
  private String createdBy;
  private long createdOn;
  private Long lastModifiedOn;
  private String lastModifiedBy;
  private Set<LexData> lexDatas = new HashSet(0);

  public LexKey() {
  }

  public LexKey(String createdBy, long createdOn) {
    this.createdBy = createdBy;
    this.createdOn = createdOn;
  }

  public LexKey(LexGroup groupId, String name, String createdBy, long createdOn, Long lastModifiedOn, String lastModifiedBy, Set<LexData> lexDatas) {
    this.groupId = groupId;
    this.name = name;
    this.createdBy = createdBy;
    this.createdOn = createdOn;
    this.lastModifiedOn = lastModifiedOn;
    this.lastModifiedBy = lastModifiedBy;
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
