package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.modules.mailing.dto.NewsletterDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.NewsletterScheduleDTO;
import com.eurodyn.qlack.fuse.modules.mailing.service.NewsletterManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * A Stateless Session EJB provides news letter related services.
 *
 * @author European Dynamics SA.
 */
@Stateless(name = "NewsletterManagerBean")
public class NewsletterManagerBean implements NewsletterManager {

  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager em;

  @Override
  public String createNewsletter(NewsletterDTO dto) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void deleteNewsleter(String newsletterID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void updateNewsletter(NewsletterDTO dto) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public NewsletterDTO getNewsletter(String newsletterID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public NewsletterDTO[] searchNewsletter(NewsletterDTO dto) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public String createSchedule(NewsletterScheduleDTO dto) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void deleteSchedule(String scheduleID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void getSchedule(String scheduleID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void addScheduleToNewsletter(String scheduleID, String newsletterID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void removeScheduleFromNewsLetter(String scheduleID, String newsletterID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void addDistributionListToNewsletter(String distributionListID, String scheduleID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void removeDistributionListFromNewsletter(String distributionListID, String scheduleID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void sendNewsletter(String newsletterID) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
