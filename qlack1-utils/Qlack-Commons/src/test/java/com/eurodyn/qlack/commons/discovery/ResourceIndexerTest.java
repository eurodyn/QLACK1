package com.eurodyn.qlack.commons.discovery;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.List;

/**
 * @author European Dynamics S.A.
 */
public class ResourceIndexerTest {

  public ResourceIndexerTest() {
  }

  /**
   * Test of getResourcesForPackage method, of class ResourceIndexer.
   */
  @Test
  public void testGetResourcesForPackage() throws Exception {
    System.out.println("getResourcesForPackage");

    String pckgname = "com.eurodyn.qlack.commons.testClasses";
    String startsWith = "M";
    String endsWith = "";
    String fileExtension = "class";
    boolean dirInfo = true;

    String expResult = "Movie.class";

    List<String> result = ResourceIndexer.getResourcesForPackage(pckgname,
        startsWith, endsWith, fileExtension, dirInfo);
    if (result != null && !result.isEmpty()) {
      String str = result.get(0);
      assertNotNull(str.contains(expResult));
    }
  }

}