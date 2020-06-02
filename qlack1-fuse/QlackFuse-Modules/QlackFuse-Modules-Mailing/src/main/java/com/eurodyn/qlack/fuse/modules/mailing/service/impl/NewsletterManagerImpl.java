package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.modules.mailing.dto.NewsletterDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.NewsletterScheduleDTO;
import com.eurodyn.qlack.fuse.modules.mailing.service.NewsletterManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "NewsletterManagerImpl")
public class NewsletterManagerImpl implements NewsletterManager {

  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager entityManager;

  @Override
  public String createNewsletter(NewsletterDTO dto) {
    return null;
  }

  @Override
  public void deleteNewsleter(String newsletterID) {

  }

  @Override
  public void updateNewsletter(NewsletterDTO dto) {

  }

  @Override
  public NewsletterDTO getNewsletter(String newsletterID) {
    return null;
  }

  @Override
  public NewsletterDTO[] searchNewsletter(NewsletterDTO dto) {
    return new NewsletterDTO[0];
  }

  @Override
  public String createSchedule(NewsletterScheduleDTO dto) {
    return null;
  }

  @Override
  public void deleteSchedule(String scheduleID) {

  }

  @Override
  public void getSchedule(String scheduleID) {

  }

  @Override
  public void addScheduleToNewsletter(String scheduleID, String newsletterID) {

  }

  @Override
  public void removeScheduleFromNewsLetter(String scheduleID, String newsletterID) {

  }

  @Override
  public void addDistributionListToNewsletter(String distributionListID, String scheduleID) {

  }

  @Override
  public void removeDistributionListFromNewsletter(String distributionListID, String scheduleID) {

  }

  @Override
  public void sendNewsletter(String newsletterID) {

  }
}
