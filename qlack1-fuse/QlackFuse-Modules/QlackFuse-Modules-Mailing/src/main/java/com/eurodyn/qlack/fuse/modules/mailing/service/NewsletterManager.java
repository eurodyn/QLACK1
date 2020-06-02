package com.eurodyn.qlack.fuse.modules.mailing.service;

import com.eurodyn.qlack.fuse.modules.mailing.dto.NewsletterDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.NewsletterScheduleDTO;
import javax.ejb.Remote;

/**
 * A Stateless Session EJB provides services to manage newsletter.
 *
 * @author European Dynamics SA.
 */
@Remote
public interface NewsletterManager {

  String createNewsletter(NewsletterDTO dto);

  void deleteNewsleter(String newsletterID);

  void updateNewsletter(NewsletterDTO dto);

  NewsletterDTO getNewsletter(String newsletterID);

  NewsletterDTO[] searchNewsletter(NewsletterDTO dto);

  String createSchedule(NewsletterScheduleDTO dto);

  void deleteSchedule(String scheduleID);

  void getSchedule(String scheduleID);

  void addScheduleToNewsletter(String scheduleID, String newsletterID);

  void removeScheduleFromNewsLetter(String scheduleID, String newsletterID);

  void addDistributionListToNewsletter(String distributionListID, String scheduleID);

  void removeDistributionListFromNewsletter(String distributionListID, String scheduleID);

  void sendNewsletter(String newsletterID);

}
