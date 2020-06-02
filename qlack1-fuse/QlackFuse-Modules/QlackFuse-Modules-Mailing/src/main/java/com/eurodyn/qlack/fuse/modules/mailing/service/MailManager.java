package com.eurodyn.qlack.fuse.modules.mailing.service;

import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO;
import javax.ejb.Remote;

import java.util.List;

/**
 * Remote Interface for Send Mail functionality.
 *
 * @author European Dynamics SA.
 */
@Remote
public interface MailManager {

  void queueEmail(EmailDTO dto);

  void queueEmails(List<EmailDTO> dtos);

  void deleteFromQueue(String emailID);

  void cleanup(Long date, EMAIL_STATUS[] status);

  void updateStatus(String id, MailManager.EMAIL_STATUS status);

  enum EMAIL_STATUS {

    QUEUED, SENT, FAILED, CANCELED
  }

}
