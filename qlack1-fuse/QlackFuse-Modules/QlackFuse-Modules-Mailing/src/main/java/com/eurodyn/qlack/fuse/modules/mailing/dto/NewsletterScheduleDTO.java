package com.eurodyn.qlack.fuse.modules.mailing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author European Dynamics SA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterScheduleDTO {

  private String id;

  private String newsletterID;

  private String scheduledFor;

  private boolean sent;

  private Long sentOn;

}
