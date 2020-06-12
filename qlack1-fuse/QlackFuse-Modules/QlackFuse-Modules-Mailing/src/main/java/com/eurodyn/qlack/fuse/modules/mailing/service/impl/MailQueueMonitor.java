package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.commons.dto.mailing.AttachmentDTO;
import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO;
import com.eurodyn.qlack.fuse.modules.mailing.exception.QlackFuseMailingException;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiAttachment;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiEmail;
import com.eurodyn.qlack.fuse.modules.mailing.service.MailManager;
import com.eurodyn.qlack.fuse.modules.mailing.service.MailManager.EMAIL_STATUS;
import com.eurodyn.qlack.fuse.modules.mailing.util.ConverterUtil;
import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA
 */
@Singleton
@Startup
public class MailQueueMonitor {

  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager em;

  @EJB(name = "MailManagerBean")
  private MailManager mailManager;

  @Resource
  private SessionContext context;

  @Resource(mappedName = "mail/QlackMailSession")
  private Session session;

  private static final Logger logger = Logger.getLogger(MailQueueMonitor.class.getName());

  // How often (in msec) to check for new queued emails.
  private static final long timerFrequency = 10000;

  // Maximum number of tries to send a previously failed e-mail.
  private final static byte maxTries = 3;

  /**
   * Initialize the MailingManager schedule with specified poll period.
   */
  @PostConstruct
  private void startup() {
    logger.log(Level.CONFIG, "Initialising MailingManager schedule with poll period {0} sec.",
        timerFrequency);

    // Remove any already running schedules.
    Collection timers = context.getTimerService().getTimers();
    for (Iterator timersI = timers.iterator(); timersI.hasNext(); ) {
      Timer timer = (Timer) timersI.next();
      if ((timer.getInfo() != null) && (timer.getInfo().equals("MailManager"))) {
        logger.log(Level.WARNING, "Found an already running MailManager which will be"
            + " stopped.");
        timer.cancel();
        break;
      }
    }

    // Create a new schedule.
    context.getTimerService().createTimer(0, timerFrequency, "MailManager");
    logger.log(Level.CONFIG, "MailingManager scheduled successfully.");
  }

  /**
   * Shut down MailingManager schedule.
   */
  @PreDestroy
  private void shutdown() {
    logger.log(Level.CONFIG, "Shutting down MailingManager schedule.");
    Collection timers = context.getTimerService().getTimers();
    for (Iterator timersI = timers.iterator(); timersI.hasNext(); ) {
      Timer timer = (Timer) timersI.next();
      if ((timer.getInfo() != null) && (timer.getInfo().equals("MailManager"))) {
        timer.cancel();
        break;
      }
    }
  }

  /**
   * Check for QUEUED emails and send them.
   */
  @Timeout
  private synchronized void checkAndSendQueued(Timer timer) {
    logger.log(Level.FINER, "checkAndSendQueued timer is fired.");
    Criteria criteria = CriteriaFactory.createCriteria("MaiEmail");
    criteria.add(Restrictions.lt("tries", maxTries));
    criteria.add(Restrictions.eq("status", EMAIL_STATUS.QUEUED.toString()));
    List<MaiEmail> emails = criteria.prepareQuery(em).getResultList();
    if (!emails.isEmpty()) {
      logger.log(Level.FINER, "Found {0} email(s) to be sent.", emails.size());
      for (Iterator<MaiEmail> emailsI = emails.iterator(); emailsI.hasNext(); ) {
        MaiEmail email = emailsI.next();
        try {
          email.setDateSent(System.currentTimeMillis());
          email.setTries((byte) (email.getTries() + 1));

          // Create a DTO for this email.
          EmailDTO dto = new EmailDTO();
          dto.setId(email.getId());
          dto.setSubject(email.getSubject());
          dto.setBody(email.getBody());
          dto.setFrom(email.getFromEmail());
          if (email.getToEmails() != null) {
            dto.setToContact(ConverterUtil.createRecepientlist(email.getToEmails()));
          }
          if (email.getCcEmails() != null) {
            dto.setCcContact(ConverterUtil.createRecepientlist(email.getCcEmails()));
          }
          if (email.getBccEmails() != null) {
            dto.setBccContact(ConverterUtil.createRecepientlist(email.getBccEmails()));
          }
          if (email.getEmailType().equals("HTML")) {
            dto.setEmailType(EmailDTO.EMAIL_TYPE.HTML);
          } else {
            dto.setEmailType(EmailDTO.EMAIL_TYPE.TEXT);
          }

          // Process attachments.
          List<AttachmentDTO> attachmentList = new ArrayList<>();
          Set<MaiAttachment> attachments = email.getMaiAttachments();
          if (attachments != null && !attachments.isEmpty()) {
            for (Iterator<MaiAttachment> aI = attachments.iterator(); aI.hasNext(); ) {
              MaiAttachment attachment = aI.next();
              AttachmentDTO attachmentDTO = new AttachmentDTO();
              attachmentDTO.setContentType(attachment.getContentType());
              attachmentDTO.setData(attachment.getData());
              attachmentDTO.setFilename(attachment.getFilename());
              attachmentList.add(attachmentDTO);
            }
            dto.setAttachments(attachmentList);
          }

          send(dto);
          email.setStatus(MailManagerBean.EMAIL_STATUS.SENT.toString());
        } catch (Exception ex) {
          // We catch an Exception here, since we do not want an error to cancel the timer.
          logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
          if (email.getTries() >= maxTries) {
            email.setStatus(MailManagerBean.EMAIL_STATUS.FAILED.toString());
            email.setServerResponse(ex.getCause() != null
                ? ex.getCause().getLocalizedMessage()
                : ex.getLocalizedMessage());
            email.setServerResponseDate(System.currentTimeMillis());
          }
        } finally {
          updateMail(email);
        }
      }
    } else {
      logger.log(Level.FINER, "Found no email(s) to be sent.");
    }
  }

  /**
   * Update the data of an email.
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  private void updateMail(MaiEmail email) {
    em.persist(email);
  }

  /**
   * Send the email.
   */
  private void send(EmailDTO vo) throws QlackFuseMailingException {
    Transport transport = null;
    try {
      // create a message
      MimeMessage msg = new MimeMessage(session);

      // Set the From field
      if (StringUtils.isEmpty(vo.getFrom())) {
        msg.setFrom(
            new javax.mail.internet.InternetAddress("Qlack Mailing <no-reply@qlack.eurodyn.com>"));
      } else {
        msg.setFrom(new javax.mail.internet.InternetAddress(vo.getFrom()));
      }

      // Set the To field. (Multiple addresses can be separated by semicolons)
      List<String> toAddresses = vo.getToContact();
      if (toAddresses != null && !toAddresses.isEmpty()) {
        msg.setRecipients(javax.mail.Message.RecipientType.TO,
            stringLToAddressL(toAddresses));
      }

      // Set the CC field.
      List<String> ccAddresses = vo.getCcContact();
      if (ccAddresses != null && !ccAddresses.isEmpty()) {
        msg.setRecipients(javax.mail.Message.RecipientType.CC,
            stringLToAddressL(ccAddresses));
      }

      // Set the BCC field.
      List<String> bccAddresses = vo.getBccContact();
      if (bccAddresses != null && !bccAddresses.isEmpty()) {
        msg.setRecipients(javax.mail.Message.RecipientType.BCC,
            stringLToAddressL(bccAddresses));
      }

      // Set the subject and date.
      msg.setSubject(vo.getSubject(), "utf-8");
      msg.setSentDate(new Date());

      // Set the content.
      // Need to work in parts in case of attachments.
      if ((vo.getAttachments() != null) && (vo.getAttachments().size() > 0)) {
        Multipart multipart = new MimeMultipart();
        // part one is the general text of the message.
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        if (vo.getEmailType() == EmailDTO.EMAIL_TYPE.TEXT) {
          messageBodyPart.setText(vo.getBody(), "utf-8");
        } else if (vo.getEmailType() == EmailDTO.EMAIL_TYPE.HTML) {
          messageBodyPart.setContent(vo.getBody(), "text/html; charset=utf-8");
        }
        multipart.addBodyPart(messageBodyPart);
        // Part two, three, four, etc. are attachments
        List<AttachmentDTO> attachmentList = vo.getAttachments();
        for (AttachmentDTO attachmentDTO : attachmentList) {
          if (attachmentDTO != null) {
            String filename = attachmentDTO.getFilename();
            byte[] data = attachmentDTO.getData();
            String contentType = attachmentDTO.getContentType();
            if (filename != null && !filename.equals("") && data != null) {
              ByteArrayDataSource mimePartDataSource = new ByteArrayDataSource(data,
                  contentType);
              MimeBodyPart attachment = new MimeBodyPart();
              attachment.setDataHandler(new DataHandler(mimePartDataSource));
              attachment.setFileName(filename);
              multipart.addBodyPart(attachment);
            }
          }
        }

        // Put parts in message
        msg.setContent(multipart);
      } else {    // A message without attachments.
        if (vo.getEmailType() == EmailDTO.EMAIL_TYPE.TEXT) {
          msg.setText(vo.getBody(), "utf-8");
        } else if (vo.getEmailType() == EmailDTO.EMAIL_TYPE.HTML) {
          msg.setContent(vo.getBody(), "text/html;charset=\"UTF-8\"");
        }
      }

      // Initiate the send process.
      transport = session.getTransport();
      Transport.send(msg);
    } catch (javax.mail.MessagingException ex) {
      throw new QlackFuseMailingException(QlackFuseMailingException.CODES.ERR_MAI_0010,
          ex.getLocalizedMessage());
    } finally {
      closeEmailTransport(transport);
    }
  }

  private Address[] stringLToAddressL(List<String> addresses) throws AddressException {
    Address[] retVal = new Address[addresses.size()];
    for (int i = 0; i < addresses.size(); i++) {
      retVal[i] = new InternetAddress(addresses.get(i));
    }
    return retVal;
  }

  private void closeEmailTransport(Transport transport) throws QlackFuseMailingException {
    try {
      if (transport != null) {
        transport.close();
      }
    } catch (MessagingException ex) {
      throw new QlackFuseMailingException(QlackFuseMailingException.CODES.ERR_MAI_0010,
          ex.getLocalizedMessage());
    }
  }


}
