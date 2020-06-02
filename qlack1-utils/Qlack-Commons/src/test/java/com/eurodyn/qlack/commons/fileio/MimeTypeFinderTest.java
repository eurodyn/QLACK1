package com.eurodyn.qlack.commons.fileio;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.io.File;
import java.util.Collection;

/**
 * @author European Dynamics S.A.
 */
public class MimeTypeFinderTest {

  public MimeTypeFinderTest() {
  }

  /**
   * Test of findMimeTypes method, of class MimeTypeFinder.
   */
  @Test
  public void testFindMimeTypes_File() throws Exception {
    System.out.println("findMimeTypes");

    File f = new File("src/test/resources/testFile.txt");
    Collection result = MimeTypeFinder.findMimeTypes(f);
    assertTrue(result.size() > 0);
  }

  /**
   * Test of findMimeTypes method, of class MimeTypeFinder.
   */
  @Test
  public void testFindMimeTypes_byteArr() {
    System.out.println("findMimeTypes");
    byte[] fileAsByteArray;
    try {
      fileAsByteArray = FileReader.readBinaryFile(new File("src/test/resources/testFile.txt"));
      Collection result = MimeTypeFinder.findMimeTypes(fileAsByteArray);
      assertTrue(result.size() > 0);
    } catch (Exception ex) {
      fail(ex.toString());
    }
  }

}