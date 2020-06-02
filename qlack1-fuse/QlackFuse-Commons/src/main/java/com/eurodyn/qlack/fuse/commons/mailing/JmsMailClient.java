package com.eurodyn.qlack.fuse.commons.mailing;

import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * A convenience class to send e-mails from your components without having to depend on the Mailing component. Note that
 * using this class you actually totally bypass the Mailing component and all of its scheduling, queuing and statistics
 * mechanisms; when possible, prefer using the queuing mechanism of Mailing component.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public class JmsMailClient {

  private ConnectionFactory connectionFactory;
  private Destination queue;

  /**
   * @param connectionFactory The connection factory to use.
   * @param queue the Queue The JMS Queue to use.
   */
  public JmsMailClient(ConnectionFactory connectionFactory, Destination queue) {
    this.connectionFactory = connectionFactory;
    this.queue = queue;
  }

  /**
   * Sends an e-mail. Note that the actual e-mail is sent by the MiniMail component and therefore its subjects to
   * any/all JMS constraints placed there (i.e. it does not necessarily mean that as soon as you call this method the
   * e-mail is sent immediately).
   *
   * @param emailDTO Containing information about the e-mail to be sent.
   * @param reportServerResponse If true, once the message is delivered by MiniMail it will try to send a reply back to
   * the Mailing component regarding the outcome of this delivery. When you use the JmsMailClient directly in your
   * component keep this parameter always 'false' as there is no entry in Mailing about the e-mail you are about to
   * send.
   * @throws JMSException if there is issue with JMS connection
   */
  public void send(EmailDTO emailDTO, boolean reportServerResponse) throws JMSException {
    Connection connection = null;
    Session session = null;
    MessageProducer producer = null;
    try {
      connection = connectionFactory.createConnection();
      connection.start();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Setup a message producer to send message to the queue the server is consuming from.
      producer = session.createProducer(queue);

      // Now create the actual message to send.
      ObjectMessage message = session.createObjectMessage();
      message.setObject(emailDTO);

      // If the user has opted to know the server response, we set an appropriate header here
      // to let the receiver know about it.
      message.setBooleanProperty("reportServerResponse", reportServerResponse);

      // Send the message.
      producer.send(message);
    } finally {
      if (producer != null) {
        producer.close();
      }
      if (session != null) {
        session.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
}
