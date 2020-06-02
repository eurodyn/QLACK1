package com.eurodyn.qlack.fuse.modules.mailing.model;

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
    name = "mai_template"
)
public class MaiTemplate implements Serializable {

  private String id;
  private String name;
  private String description;
  private String body;
  private Set<MaiNewsletter> maiNewsletters = new HashSet(0);

  public MaiTemplate() {
  }

  public MaiTemplate(String name, String description, String body, Set<MaiNewsletter> maiNewsletters) {
    this.name = name;
    this.description = description;
    this.body = body;
    this.maiNewsletters = maiNewsletters;
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
      length = 45
  )
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(
      name = "description"
  )
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(
      name = "body",
      length = 65535
  )
  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "templateId"
  )
  public Set<MaiNewsletter> getMaiNewsletters() {
    return this.maiNewsletters;
  }

  public void setMaiNewsletters(Set<MaiNewsletter> maiNewsletters) {
    this.maiNewsletters = maiNewsletters;
  }
}
