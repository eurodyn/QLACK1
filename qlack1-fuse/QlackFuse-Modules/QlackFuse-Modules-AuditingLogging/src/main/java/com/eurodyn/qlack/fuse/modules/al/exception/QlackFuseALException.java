package com.eurodyn.qlack.fuse.modules.al.exception;

import com.eurodyn.qlack.fuse.commons.exception.QlackFuseException;
import javax.ejb.ApplicationException;

/**
 * Qlack AL module generic exception
 *
 * @author European Dynamics SA
 */
@ApplicationException(rollback = true)
public class QlackFuseALException extends QlackFuseException {

  /**
   * Constructor with code and message
   *
   * @param errorCode the error code
   * @param message the exception message
   */
  public QlackFuseALException(CODES errorCode, String message) {
    super(errorCode, message);
  }

  public enum CODES implements ExceptionCode {
    ERR_AL_0001, //Duplicate audit level.
    ERR_AL_0002, //Audit level not found.
    ERR_AL_0003, //Audit log not found.
    ERR_AL_0004 //Exception whild converting audit list to xml.
  }

}
