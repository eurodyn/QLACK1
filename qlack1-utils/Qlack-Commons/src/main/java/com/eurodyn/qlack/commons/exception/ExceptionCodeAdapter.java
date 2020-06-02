/*
 */

package com.eurodyn.qlack.commons.exception;

import com.eurodyn.qlack.commons.exception.QlackException.ExceptionCode;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author European Dynamics SA
 */
class ExceptionCodeAdapter extends XmlAdapter<ExceptionCodeImpl, ExceptionCode> {

  /**
   * Convert an ExceptionCodeImpl to ExceptionCode.
   */
  @Override
  public ExceptionCode unmarshal(ExceptionCodeImpl obj1) {
    ExceptionCode r = (ExceptionCode) obj1;
    return r;
  }

  /**
   * Convert an ExceptionCode to ExceptionCodeImpl.
   */
  @Override
  public ExceptionCodeImpl marshal(ExceptionCode obj1) {
    ExceptionCodeImpl r = (ExceptionCodeImpl) obj1;
    return r;
  }
}
