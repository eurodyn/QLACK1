package com.eurodyn.qlack.fuse.commons.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * @author EUROPEAN DYNAMICS SA.
 */
public class Messenger {

  private static final Logger logger = Logger.getLogger(Messenger.class.getName());

  private Messenger() {
  }

  /**
   * Post a message.
   */
  public static void post(ConnectionFactory connectionFactory, Destination destination,
      QlackMessage message) throws JMSException {

    Connection connection = null;
    Session session = null;
    MessageProducer msgProducer = null;
    try {
      logger.info("Posting a new message on JMS channel " + destination.toString());
      // Create the message producer.
      connection = connectionFactory.createConnection();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Create a new ObjectMessage and populate it with QlackMessage.
      // First, set the properties of the message.
      ObjectMessage jmsMessage = session.createObjectMessage();
      HashMap<String, Object> msgProperties = message.getAllProperties();
      for (Entry<String, Object> key : msgProperties.entrySet()) {
        if (key.getValue() instanceof String) {
          jmsMessage.setStringProperty(key.getKey(), (String) key.getValue());
        }
      }
      // Second, set the body of the message.
      if (message.getBody() != null) {
        jmsMessage.setObject(message.getBody());
      }

      // Publish the message.
      msgProducer = session.createProducer(destination);
      msgProducer.send(jmsMessage);
    } finally {
      if (session != null) {
        session.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
}
