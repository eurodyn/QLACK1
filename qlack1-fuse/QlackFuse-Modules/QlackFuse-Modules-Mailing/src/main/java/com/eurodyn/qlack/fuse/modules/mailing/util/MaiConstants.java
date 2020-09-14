package com.eurodyn.qlack.fuse.modules.mailing.util;

/**
 * Constants to be used in Mailing.
 *
 * @author European Dynamics SA.
 */
public class MaiConstants {

  public static final String COMMA_TOKEN = ",";
  public static final String SEMI_COLON_TOKEN = ";";
  public static final String INACTIVE_STATUS = "INACTIVE";
  public static final String ACTIVE_STATUS = "ACTIVE";
  public static final boolean DL_ACTIVE_CONTACT = true;
  public static final boolean DL_INACTIVE_CONTACT = false;
  public static final String DAILY_RECURRING_OPTION = "DAILY";
  public static final String WEEKLY_RECURRING_OPTION = "WEEKLY";
  public static final String EMAIL_TYPE = "E";
  public static final String NL_TYPE = "N";
  public static final String MARK_READ = "READ";
  public static final String MARK_UNREAD = "UNREAD";
  public static final String MARK_REPLIED = "REPLIED";
  public static final String INBOX_FOLDER_TYPE = "INBOX";
  public static final String SENT_FOLDER_TYPE = "SENT";
  public static final String LOG = "MaiLog";
  public static final String LOG_MESSAGEID = "messageId";
  public static final String LOG_SUBJECT = "subject";
  public static final String EMAIL = "MaiEmail";
  public static final String EMAIL_ID = "id";
  public static final String EMAIL_STAUTS = "status";
  public static final String EMAIL_SUBJECT = "subject";
  public static final String EMAIL_DATE_SENT = "dateSent";
  public static final String NEWSLETTER = "MaiNewsletter";
  public static final String NEWSLETTER_ID = "id";
  public static final String NEWSLETTER_STATUS = "status";
  public static final String NEWSLETTER_DESCRIPTION = "description";
  public static final String NEWSLETTER_DL_ID = "distributionListId.id";
  public static final String DL_HAS_CONTACT = "MaiDistributionListHasContact";
  public static final String DL_HAS_CONTACT_DL_ID = "distributionListId.id";
  public static final String DL_HAS_CONTACT_ACTIVE = "active";
  public static final String DL_HAS_CONTACT_ID = "contactId.id";
  public static final String CONTACT = "MaiContact";
  public static final String CONTACT_USERID = "userid";
  public static final String DISTRIBUTIONLIST = "MaiDistributionList";
  public static final String DISTRIBUTIONLIST_NAME = "listName";
  public static final String INTERNAL_MSG = "MaiInternalMessages";
  public static final String INTERNAL_MSG_TO = "mailTo";
  public static final String INTERNAL_MSG_FRM = "mailFrom";
  public static final String INTERNAL_MSG_DELETE_TYPE = "deleteType";
  public static final String INTERNAL_ATTACHMENT = "MaiInternalAttachment";
  public static final String INTERNAL_ATTACHMENT_ID = "id";
  public static final String INTERNAL_ATTACHMENT_MAIL_ID = "messagesId.id";
  public static final String NL_SCHEDULE = "MaiNewsletterSchedule";
  public static final String NL_SCHEDULE_ID = "id";
  public static final String NL_SCHEDULE_NL_ID = "newsletterId.id";
  private MaiConstants() {
  }
}
