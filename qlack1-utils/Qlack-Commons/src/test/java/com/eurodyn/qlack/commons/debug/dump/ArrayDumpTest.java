package com.eurodyn.qlack.commons.debug.dump;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.eurodyn.qlack.commons.testClasses.Person;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author European Dynamics S.A.
 */
public class ArrayDumpTest {

  public ArrayDumpTest() {
  }

  /**
   * Test of dump method, of class ArrayDump.
   */
  @Test
  public void testDump() {
    System.out.println("dump");

    Person[] directors = {getDirector1(), getDirector2()};
    try {
      ArrayDump.dump(directors);
    } catch (Exception e) {
      fail("This exception should not happen: " + e);
    }
  }

  /**
   * Test of stringify method, of class ArrayDump.
   */
  @Test
  public void testStringify() {
    System.out.println("stringify");

    String[] array = {"One", "Two", "Three", "Four", "Five"};
    String delimiter = "#";
    String result = ArrayDump.stringify(array, delimiter);
    assertEquals("One#Two#Three#Four#Five", result);
  }


  private Person getDirector1() {
    Person director = new Person();
    director.setName("Peter Collinson");
    director.setGender(1);

    Map director_contacts = new HashMap();
    director_contacts.put("Home", "99922233");
    director_contacts.put("Mobile", "0343343433");
    director.setContactNumber(director_contacts);

    return director;
  }

  private Person getDirector2() {
    Person director = new Person();
    director.setName("George Lucas");
    director.setGender(1);

    Map director_contacts = new HashMap();
    director_contacts.put("Home", "99922255");
    director_contacts.put("Mobile", "0345543455");
    director.setContactNumber(director_contacts);

    return director;
  }

}