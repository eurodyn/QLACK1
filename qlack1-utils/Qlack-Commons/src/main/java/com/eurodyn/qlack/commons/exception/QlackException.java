package com.eurodyn.qlack.commons.exception;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;

/**
 * @author European Dynamics SA
 */
public abstract class QlackException extends Exception implements Serializable {

  private static final long serialVersionUID = -7307004865380436037L;

  /**
   * The error code.
   */
  private final transient ExceptionCode errorCode;

  /**
   * The error message.
   */
  private final String message;

  /**
   * Set the errorCode and the message for the PhoenixException.
   */
  public QlackException(ExceptionCode errorCode, String message) {
    super(errorCode + ": " + message);
    this.errorCode = errorCode;
    this.message = message;
  }

  /**
   * Get  the errorCode of the QlackException.
   *
   * @return ExceptionCode
   */
  public ExceptionCode getQlackErrorCode() {
    return errorCode;
  }

  /**
   * Get the message of the QlackException.
   *
   * @return String
   */
  public String getQlackErrorMessage() {
    return message;
  }


  @XmlType
  @XmlJavaTypeAdapter(value = ExceptionCodeAdapter.class)
  protected interface ExceptionCode {

  }
}
