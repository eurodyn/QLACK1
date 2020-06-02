package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MailQueueMonitor {

  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager entityManager;

  @Resource(mappedName = "mail/QlackMailSession")
  private Session session;

}
