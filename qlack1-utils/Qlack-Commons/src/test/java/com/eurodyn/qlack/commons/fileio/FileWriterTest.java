package com.eurodyn.qlack.commons.fileio;

import static org.junit.Assert.fail;

import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author European Dynamics S.A.
 */
public class FileWriterTest {

  public FileWriterTest() {
  }

  /**
   * Test of writeBinary method, of class FileWriter.
   */
  @Test
  public void testWriteBinary_String_byteArr() throws Exception {
    System.out.println("writeBinary");

    String filename = "src/test/resources/testFile.txt";
    String dataStr = "This is the data string.";
    byte[] data = dataStr.getBytes(StandardCharsets.UTF_8);
    try {
      FileWriter.writeBinary(filename, data);
    } catch (Exception e) {
      fail(e.toString());
    }
  }

  /**
   * Test of writeBinary method, of class FileWriter.
   */
  @Test
  public void testWriteBinary_File_byteArr() throws Exception {
    System.out.println("writeBinary");

    File file = new File("src/test/resources/testFile.txt");
    String dataStr = "This is the data string.";
    byte[] data = dataStr.getBytes(StandardCharsets.UTF_8);
    try {
      FileWriter.writeBinary(file, data);
    } catch (Exception e) {
      fail(e.toString());
    }

  }
}