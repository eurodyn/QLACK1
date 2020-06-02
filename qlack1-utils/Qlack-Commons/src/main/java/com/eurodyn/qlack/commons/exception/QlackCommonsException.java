package com.eurodyn.qlack.commons.exception;

/**
 * @author European Dynamics SA
 */
public class QlackCommonsException extends QlackException {

  private static final long serialVersionUID = 6837724370378230266L;

  /**
   * Initializes a PhoenixException with the errorCode and the message.
   */
  public QlackCommonsException(ExceptionCode errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * Enum of Exception codes for PhoenixCommonsException .
   */
  public enum CODES implements ExceptionCode {
    ERR_COM_0000   // A generic exception code to wrap other exceptions we do not want
    // to bubble up.
  }
}
