package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.commons.dto.mailing.AttachmentDTO;
import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiAttachment;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiEmail;
import com.eurodyn.qlack.fuse.modules.mailing.service.MailManager;
import com.eurodyn.qlack.fuse.modules.mailing.util.ConverterUtil;
import com.eurodyn.qlack.fuse.modules.mailing.util.CriteriaBuilderUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.restrictions.Disjunction;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Bean implementation for Send and Search Mail functionality
 *
 * @author European Dynamics SA.
 */
@Stateless(name = "MailManagerBean")
public class MailManagerBean implements MailManager {

  private static final Logger logger = Logger.getLogger(MailManagerBean.class.getName());
  private final CriteriaBuilderUtil criteriaBuilderUtil = new CriteriaBuilderUtil("MaiEmail");
  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager em;

  /**
   * Queue a list of Emails.
   *
   * @param dto - list of email data transfer objects.
   */
  @Override
  public void queueEmails(List<EmailDTO> dtos) {
    for (EmailDTO dto : dtos) {
      queueEmail(dto);
    }
  }

  /**
   * Queue an email.
   *
   * @param dto - an email data transfer object.
   */
  @Override
  public void queueEmail(EmailDTO dto) {
    MaiEmail entity = new MaiEmail();
    entity.setId(dto.getId());
    entity.setSubject(dto.getSubject());
    entity.setBody(dto.getBody());
    entity.setDateSent(dto.getDateSent() != null ? dto.getDateSent().getTime() : null);
    entity.setServerResponse(dto.getServerResponse());
    entity.setStatus(dto.getStatus());
    entity.setFromEmail(dto.getFrom());
    entity.setToEmails(ConverterUtil.createRecepientlist(dto.getToContact()));
    entity.setCcEmails(ConverterUtil.createRecepientlist(dto.getCcContact()));
    entity.setBccEmails(ConverterUtil.createRecepientlist(dto.getBccContact()));
    entity.setEmailType(dto.getEmailType().toString());
    entity.setStatus(EMAIL_STATUS.QUEUED.toString());
    entity.setAddedOnDate(System.currentTimeMillis());
    entity.setTries((byte) 0);
    em.persist(entity);

    // Process attachments.
    if (dto.getAttachments() != null && !dto.getAttachments().isEmpty()) {
      Set<MaiAttachment> attachments = new HashSet<>();
      for (AttachmentDTO aDTO : dto.getAttachments()) {
        MaiAttachment attachment = new MaiAttachment();
        attachment.setContentType(aDTO.getContentType());
        attachment.setData(aDTO.getData());
        attachment.setFilename(aDTO.getFilename());
        attachment.setEmailId(entity);
        attachment.setAttachmentSize((long) aDTO.getData().length);
        em.persist(attachment);
        attachments.add(attachment);
      }
      entity.setMaiAttachments(attachments);
    }
  }

  /**
   * Removes all e-mails prior to the specified date having the requested status. Warning: If you pass a
   * <code>null</code> date all emails irrespectively of date will be removed.
   *
   * @param date the date before which all e-mails will be removed.
   * @param status the status to be processed. Be cautious to not include e-mails of status QUEUED as such e-mails might
   * not have been tried to be delivered yet.
   */
  @Override
  public void cleanup(Long date, EMAIL_STATUS[] status) {
    Criteria criteria = criteriaBuilderUtil.getCriteria();

    // Add status.
    if (status != null && status.length > 0) {
      Disjunction dis = Restrictions.disjunction();
      for (EMAIL_STATUS nextStatus : status) {
        dis.add(Restrictions.eq("status", nextStatus.toString()));
      }
      criteria.add(dis);
    }

    // Add date.
    if (date != null) {
      criteria.add(Restrictions.le("addedOnDate", date));
    }

    // Delete matching emails.
    em.setFlushMode(FlushModeType.COMMIT);
    List<MaiEmail> l = criteria.prepareQuery(em).getResultList();
    for (MaiEmail maiEmail : l) {
      em.remove(maiEmail);
    }
  }

  /**
   * Delete an email from the queue.
   *
   * @param emailID - the email id.
   */
  @Override
  public void deleteFromQueue(String emailID) {
    MaiEmail mail = getMailByID(emailID);
    em.remove(mail);
  }

  /**
   * Update the status of an email.
   *
   * @param id - the email id.
   * @param status - the email status
   */
  @Override
  public void updateStatus(String id, MailManager.EMAIL_STATUS status) {
    MaiEmail email = getMailByID(id);
    email.setStatus(status.toString());
  }

  private MaiEmail getMailByID(String mailID) {
    return em.find(MaiEmail.class, mailID);
  }
}
