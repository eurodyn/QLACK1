package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO;
import com.eurodyn.qlack.fuse.modules.mailing.service.MailManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Stateless(name = "MailManagerImpl")
public class MailManagerImpl implements MailManager {

  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager entityManager;

  @Override
  public void queueEmail(EmailDTO dto) {

  }

  @Override
  public void queueEmails(List<EmailDTO> dtos) {

  }

  @Override
  public void deleteFromQueue(String emailID) {

  }

  @Override
  public void cleanup(Long date, EMAIL_STATUS[] status) {

  }

  @Override
  public void updateStatus(String id, EMAIL_STATUS status) {

  }
}
