package com.eurodyn.qlack.fuse.commons.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author EUROPEAN DYNAMICS SA.
 */
@Slf4j
public class Messenger {

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
      log.info("Posting a new message on JMS channel {0}.", destination.toString());
      // Create the message producer.
      connection = connectionFactory.createConnection();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Create a new ObjectMessage and populate it with QlackMessage.
      // First, set the properties of the message.
      ObjectMessage jmsMessage = session.createObjectMessage();
      HashMap<String, Object> msgProperties = message.getAllProperties();
      for (String key : msgProperties.keySet()) {
        Object val = msgProperties.get(key);
        if (val instanceof String) {
          jmsMessage.setStringProperty(key, (String) val);
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