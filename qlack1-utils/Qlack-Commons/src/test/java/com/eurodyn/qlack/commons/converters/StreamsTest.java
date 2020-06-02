package com.eurodyn.qlack.commons.converters;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author European Dynamics S.A.
 */
public class StreamsTest {

  public StreamsTest() {
  }

  /**
   * Test of inputStreamToByteArray method, of class Streams.
   */
  @Test
  public void testInputStreamToByteArray() throws Exception {
    System.out.println("inputStreamToByteArray");

    InputStream file_is = new FileInputStream(new File("src/test/resources/testFile.txt"));
    byte[] result = Streams.inputStreamToByteArray(file_is);
    assertNotNull(result);
  }
}