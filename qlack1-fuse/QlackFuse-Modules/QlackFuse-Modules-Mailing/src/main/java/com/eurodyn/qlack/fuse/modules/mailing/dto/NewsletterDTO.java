package com.eurodyn.qlack.fuse.modules.mailing.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Data transfer object for newsletters.
 *
 * @author European Dynamics SA.
 */
public class NewsletterDTO implements Serializable {

  private String id;

  private String description;

  private String templateID;

  private String title;

  private List<DistributionListDTO> distributionLists;

  private transient List<NewsletterScheduleDTO> schedule;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTemplateID() {
    return templateID;
  }

  public void setTemplateID(String templateID) {
    this.templateID = templateID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<DistributionListDTO> getDistributionLists() {
    return distributionLists;
  }

  public void setDistributionLists(
      List<DistributionListDTO> distributionLists) {
    this.distributionLists = distributionLists;
  }

  public List<NewsletterScheduleDTO> getSchedule() {
    return schedule;
  }

  public void setSchedule(List<NewsletterScheduleDTO> schedule) {
    this.schedule = schedule;
  }
}
