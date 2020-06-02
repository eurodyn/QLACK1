package com.eurodyn.qlack.commons.converters;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author European Dynamics SA
 */
public class Streams {

  private Streams() {
  }

  /**
   * Convert InputStream to ByteArray.
   */
  public static byte[] inputStreamToByteArray(InputStream is) throws IOException {
    return IOUtils.toByteArray(is);
  }
}
