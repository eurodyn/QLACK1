package com.eurodyn.qlack.fuse.modules.mailing.util;

import com.eurodyn.qlack.fuse.commons.messaging.QlackMessage;

/**
 * @author European Dynamics SA
 */
public class MailingMessage extends QlackMessage {

  /**
   * The message type for sent email.
   */
  public static final String MSGTYPE__MAIL_SENT = "MAIL_SENT";
  /**
   * The property user id.
   */
  public static final String PROPERTY__TO_USER_ID = "USER_ID";
  /**
   * The component's name
   */
  private static final String COMPONENT_NAME = "Mailing";

  /**
   * Constructor for the MailingMessage
   */
  public MailingMessage() {
    this.setComponent(COMPONENT_NAME);
  }

}