package com.eurodyn.qlack.fuse.commons.messaging;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The basis for all real-time messages used in Qlack Fuse. This is an abstract class therefore each component in need
 * of messaging functionality needs to provide its own implementation.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public abstract class QlackMessage implements Serializable {

  public static final String SYSTEM = "SYSTEM";
  /**
   * The User ID of the user executing the action that generated this message. Note that it is up to the creator of the
   * message to set this ID to anything relevant to the actual implementation, therefore this ID should not be used for
   * security-related actions. Also note that, usually, components tend to leave this value as 'null' unless there is
   * some real need to know the user ID.
   */
  public static final String PROPERTY__SRC_USERID = "SRC_USERID";
  /**
   * The name of the component that created this message. (i.e. Chat, Forum, etc.)
   */
  public static final String PROPERTY__COMPONENT = "COMPONENT";
  /**
   * The type of this message is component-specific. (i.e. CREATE_ROOM, CREATE_TOPIC, etc.)
   */
  public static final String PROPERTY__TYPE = "TYPE";
  /**
   * Allows callers to specify that a balloon should automatically be created for this notification.
   */
  public static final String PROPERTY__AUTOBALLOON = "AUTO_BALLOON";
  /* JMS messages that should be delivered to a particular recipient should indicate
  /*
   * JMS messages that should be delivered to a particular recipient should indicate
   * the recipient's user ID specifying this JMS property.
   */
  public static final String PRIVATE_USERID = "PRIVATE_USERID";
  private HashMap<String, Object> messageProperties = new HashMap<>();
  private Serializable msgBody;

  /**
   * Get the user Id.
   */
  public String getSrcUserID() {
    Object retVal = messageProperties.get(PROPERTY__SRC_USERID);
    if (retVal != null) {
      return (String) retVal;
    } else {
      return null;
    }
  }

  /**
   * Set the source user Id.
   */
  public void setSrcUserID(String srcUserID) {
    messageProperties.put(PROPERTY__SRC_USERID, srcUserID);
  }

  /**
   * Get the component.
   */
  public String getComponent() {
    Object retVal = messageProperties.get(PROPERTY__COMPONENT);
    if (retVal != null) {
      return (String) retVal;
    } else {
      return null;
    }
  }

  /**
   * Set the component.
   */
  public void setComponent(String component) {
    messageProperties.put(PROPERTY__COMPONENT, component);
  }

  /**
   * Get the type.
   *
   * @return String Type
   */
  public String getType() {
    Object retVal = messageProperties.get(PROPERTY__TYPE);
    if (retVal != null) {
      return (String) retVal;
    } else {
      return null;
    }
  }

  /**
   * Set the type.
   */
  public void setType(String type) {
    messageProperties.put(PROPERTY__TYPE, type);
  }

  /**
   * Allows setting an arbitrary String property.
   */
  public void setStringProperty(String propertyName, String propertyValue) {
    messageProperties.put(propertyName, propertyValue);
  }

  /**
   * Allows retrieving an arbitrary String property.
   *
   * @return String property
   */
  public String getStringProperty(String propertyName) {
    Object retVal = messageProperties.get(propertyName);
    if (retVal != null) {
      return (String) retVal;
    } else {
      return null;
    }
  }

  /**
   * Get all a Map with all the properties.
   *
   * @return HashMap<String, Object>
   */
  public HashMap<String, Object> getAllProperties() {
    return messageProperties;
  }

  /**
   * Get the Body.
   */
  public Serializable getBody() {
    return msgBody;
  }

  /**
   * Set the body.
   */
  public void setBody(Serializable body) {
    msgBody = body;
  }

}