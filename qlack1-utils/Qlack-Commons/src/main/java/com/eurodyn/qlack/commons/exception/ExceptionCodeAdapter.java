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
    return (ExceptionCode) obj1;
  }

  /**
   * Convert an ExceptionCode to ExceptionCodeImpl.
   */
  @Override
  public ExceptionCodeImpl marshal(ExceptionCode obj1) {
    return (ExceptionCodeImpl) obj1;
  }
}
