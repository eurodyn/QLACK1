package com.eurodyn.qlack.commons.debug.dump;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.eurodyn.qlack.commons.testClasses.Actor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author European Dynamics S.A.
 */
public class DumperTest {

  public DumperTest() {
  }

  /**
   * Test of dumpList method, of class Dumper.
   */
  @Test
  public void testDumpList() {
    System.out.println("dumpList");

    List l = new ArrayList();
    l.add("One");
    l.add(2);
    l.add("Three");
    l.add(4);
    String expResult = "One, 2, Three, 4";

    String result = Dumper.dumpList(l);
    assertEquals(expResult, result);
  }

  /**
   * Test of dumpSet method, of class Dumper.
   */
  @Test
  public void testDumpSet() {
    System.out.println("dumpSet");

    Set s = new HashSet();
    s.add("One");
    s.add(2);
    s.add("Three");
    s.add(null);
    s.add(4);
    String expResult = "";

    String result = Dumper.dumpSet(s);
    assertTrue(result.contains("One"));
    assertTrue(result.contains("Three"));
    assertTrue(result.contains("2"));
    assertTrue(result.contains("NULL"));
    assertTrue(result.contains("4"));
  }

  /**
   * Test of dumpMap method, of class Dumper.
   */
  @Test
  public void testDumpMap() {
    System.out.println("dumpMap");

    Map<String, Actor> m = new HashMap<String, Actor>();
    Actor actor1 = new Actor();
    actor1.setName("Hayden Christensen");
    actor1.setGender(1);
    m.put("One", actor1);
    Actor actor2 = new Actor();
    actor2.setName("Natalie Portman");
    actor2.setGender(2);
    m.put("Two", actor2);

    String result = Dumper.dumpMap(m);
    assertTrue(result.contains("One"));
    assertTrue(result.contains("Hayden Christensen"));
    assertTrue(result.contains("Two"));
    assertTrue(result.contains("Natalie Portman"));
  }

  /**
   * Test of dumpMap method, of class Dumper. null Map was provided
   */
  @Test
  public void testDumpMap_null() {
    System.out.println("dumpMap_null");

    Map<String, Actor> m = null;
    String result = Dumper.dumpMap(m);
    assertEquals("Provided Map was null.", result);
  }

}