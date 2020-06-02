package com.eurodyn.qlack.fuse.modules.mailing.exception;

import com.eurodyn.qlack.fuse.commons.exception.QlackFuseException;
import javax.ejb.ApplicationException;

/**
 * This exception class having different types of exceptions which occurs in Mailing module.
 *
 * @author European Dynamics SA
 */
@ApplicationException(rollback = true)
public class QlackFuseMailingException extends QlackFuseException {

  /**
   * constructor to create instance of QlackFuseMailingException
   */
  public QlackFuseMailingException(CODES errorCode, String message) {
    super(errorCode, message);
  }

  public enum CODES implements ExceptionCode {
    ERR_MAI_0001,       // Scheduling configuration is not proper
    ERR_MAI_0002,       // DL is already assigned
    ERR_MAI_0003,       // DL is already present
    ERR_MAI_0004,       // Invalid Newsletter id provided
    ERR_MAI_0005,       // Invalid Contact provided
    ERR_MAI_0006,       // Invalid frequency provided
    ERR_MAI_0007,       // Invalid DL provided
    ERR_MAI_0008,       // No email found
    ERR_MAI_0009,       // JMS error
    ERR_MAI_0010        // Error in mail Transport
  }

}
