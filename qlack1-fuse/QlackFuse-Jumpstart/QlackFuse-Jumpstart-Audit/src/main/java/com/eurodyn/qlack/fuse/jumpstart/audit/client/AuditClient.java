package com.eurodyn.qlack.fuse.jumpstart.audit.client;

import com.eurodyn.qlack.fuse.jumpstart.lookups.guice.injectors.ContextSingleton;
import com.eurodyn.qlack.fuse.modules.al.dto.AuditLogDTO;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA.
 */
public class AuditClient {

  private ConnectionFactory connectionFactory;
  private Connection connection;
  private Queue queue;
  private static final Logger logger = Logger.getLogger(AuditClient.class.getName());
  private static AuditClient _instance = new AuditClient();

  private AuditClient() {
    logger.log(Level.CONFIG, "Initialising AuditClient.");
    try {
      connectionFactory = (ConnectionFactory) ContextSingleton.getInstance()
          .lookup("jms/Qlack_JMS_Connector");
      queue = (javax.jms.Queue) ContextSingleton.getInstance().lookup("jms/AuditQueue");
      connection = connectionFactory.createConnection();
    } catch (Exception ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
    }
  }

  public static AuditClient getInstance() {
    return AuditClientHolder.INSTANCE;
  }

  private static class AuditClientHolder {

    private static final AuditClient INSTANCE = new AuditClient();
  }

  public void audit(AuditLogDTO auditDTO) {
    Session session = null;
    MessageProducer messageProducer = null;
    try {
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      messageProducer = session.createProducer(queue);
      Message msg = session.createObjectMessage(auditDTO);
      messageProducer.send(msg);
    } catch (JMSException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
    } finally {
      if (messageProducer != null) {
        try {
          messageProducer.close();
        } catch (JMSException ex) {
          logger.log(Level.SEVERE, "Unable to close messageProducer " + ex.getMessage(), ex);
        }
      }
      if (session != null) {
        try {
          session.close();
        } catch (JMSException ex) {
          logger.log(Level.SEVERE, "Unable to close JMS Session " + ex.getMessage(), ex);
        }
      }
    }
  }
}
