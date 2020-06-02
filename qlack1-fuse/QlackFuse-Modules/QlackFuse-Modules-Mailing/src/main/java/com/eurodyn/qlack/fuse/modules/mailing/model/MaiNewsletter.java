package com.eurodyn.qlack.fuse.modules.mailing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
    name = "mai_newsletter"
)
public class MaiNewsletter implements Serializable {

  private String id;
  private MaiTemplate templateId;
  private String description;
  private String title;
  private Set<MaiDistributionList> maiDistributionLists = new HashSet(0);

  public MaiNewsletter() {
  }

  public MaiNewsletter(MaiTemplate templateId, String title) {
    this.templateId = templateId;
    this.title = title;
  }

  public MaiNewsletter(MaiTemplate templateId, String description, String title,
      Set<MaiDistributionList> maiDistributionLists) {
    this.templateId = templateId;
    this.description = description;
    this.title = title;
    this.maiDistributionLists = maiDistributionLists;
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
      name = "template_id",
      nullable = false
  )
  public MaiTemplate getTemplateId() {
    return this.templateId;
  }

  public void setTemplateId(MaiTemplate templateId) {
    this.templateId = templateId;
  }

  @Column(
      name = "description",
      length = 512
  )
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(
      name = "title",
      nullable = false,
      length = 254
  )
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @ManyToMany(
      fetch = FetchType.LAZY
  )
  @JoinTable(
      name = "mai_newsletter_has_dl",
      joinColumns = {@JoinColumn(
          name = "newsletter_id",
          nullable = false,
          updatable = false
      )},
      inverseJoinColumns = {@JoinColumn(
          name = "dlist_id",
          nullable = false,
          updatable = false
      )}
  )
  public Set<MaiDistributionList> getMaiDistributionLists() {
    return this.maiDistributionLists;
  }

  public void setMaiDistributionLists(Set<MaiDistributionList> maiDistributionLists) {
    this.maiDistributionLists = maiDistributionLists;
  }
}
