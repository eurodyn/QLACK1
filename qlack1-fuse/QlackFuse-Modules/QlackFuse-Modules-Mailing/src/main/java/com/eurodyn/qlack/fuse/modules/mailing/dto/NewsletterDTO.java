package com.eurodyn.qlack.fuse.modules.mailing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Data transfer object for newsletters.
 *
 * @author European Dynamics SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterDTO implements Serializable {

  private String id;

  private String description;

  private String templateID;

  private String title;

  private List<DistributionListDTO> distributionLists;

  private transient List<NewsletterScheduleDTO> schedule;

}
