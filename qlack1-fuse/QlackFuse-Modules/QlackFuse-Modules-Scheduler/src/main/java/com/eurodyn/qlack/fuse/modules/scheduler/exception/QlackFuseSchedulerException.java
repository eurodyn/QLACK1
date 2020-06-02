package com.eurodyn.qlack.fuse.modules.scheduler.exception;

import com.eurodyn.qlack.fuse.commons.exception.QlackFuseException;
import javax.ejb.ApplicationException;

/**
 * This is QlackFuseSchedulerException incase of any failure in Scheduler module
 *
 * @author European Dynamics SA.
 */
@ApplicationException(rollback = true)
public class QlackFuseSchedulerException extends QlackFuseException {

  /**
   * Constructs an instance of <code>QlackFuseSchedulerException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public QlackFuseSchedulerException(CODES errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * Enumeration of the error codes.
   */
  public enum CODES implements ExceptionCode {
    ERR_SCH_0001,   // Generic Scheduler exception
    ERR_SCH_0002,   // Could not schedule job
    ERR_SCH_0003,   // Could not build Job due to empty Job class
    ERR_SCH_0004,   // Error in serialising/deserialising class
    ERR_SCH_0005    // Trigger is missing required data
  }

}
