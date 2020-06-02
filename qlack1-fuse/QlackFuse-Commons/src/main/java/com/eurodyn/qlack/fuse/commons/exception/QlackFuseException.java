package com.eurodyn.qlack.fuse.commons.exception;

import com.eurodyn.qlack.commons.exception.QlackException;
import javax.ejb.ApplicationException;

/**
 * DTO class for Attribute
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@ApplicationException(rollback = true)
public class QlackFuseException extends QlackException {

  /**
   * Constructor
   */
  public QlackFuseException(ExceptionCode errorCode, String message) {
    super(errorCode, message);
  }


}