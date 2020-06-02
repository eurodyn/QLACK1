package com.eurodyn.qlack.commons.fileio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.io.File;
import java.util.Properties;

/**
 * @author European Dynamics S.A.
 */
public class FileReaderTest {

  public FileReaderTest() {
  }

  /**
   * Test of readBinaryFile method, of class FileReader.
   */
  @Test
  public void testReadBinaryFile_String() throws Exception {
    System.out.println("readBinaryFile");

    String filename = "src/test/resources/testFile.txt";
    byte[] result = FileReader.readBinaryFile(filename);
    assertNotNull(result);
  }

  /**
   * Test of readBinaryFile method, of class FileReader.
   */
  @Test
  public void testReadBinaryFile_File() throws Exception {
    System.out.println("readBinaryFile");

    File file = new File("src/test/resources/testFile.txt");
    byte[] result = FileReader.readBinaryFile(file);
    assertNotNull(result);
  }

  /**
   * Test of loadProperties method, of class FileReader.
   */
  @Test
  public void testLoadProperties() throws Exception {
    System.out.println("loadProperties");

    Class clazz = this.getClass();
    String fileName = "testFile.properties";

    Properties result = FileReader.loadProperties(clazz, fileName);
    assertEquals("Hello World", result.getProperty("hello.world"));
    assertEquals("Test property", result.getProperty("test.property"));
  }

}