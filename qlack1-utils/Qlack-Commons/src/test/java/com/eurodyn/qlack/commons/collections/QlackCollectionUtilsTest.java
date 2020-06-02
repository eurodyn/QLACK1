package com.eurodyn.qlack.commons.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author European Dynamics S.A.
 */
public class QlackCollectionUtilsTest {

  public QlackCollectionUtilsTest() {

  }

  /**
   * Test of isEmpty method, of class PhoenixCollectionUtils.
   */
  @Test
  public void testIsEmpty_false() {
    System.out.println("isEmpty");

    Collection c = new ArrayList();
    String str = "new String";
    int i = 4;
    c.add(str);
    c.add(i);

    boolean result = QlackCollectionUtils.isEmpty(c);
    assertEquals(false, result);

  }

  /**
   * Test of isEmpty method, of class PhoenixCollectionUtils.
   */
  @Test
  public void testIsEmpty_null() {
    System.out.println("isEmpty");

    Collection c = null;

    boolean result = QlackCollectionUtils.isEmpty(c);
    assertEquals(false, result);
  }

  /**
   * Test of isEmpty method, of class PhoenixCollectionUtils.
   */
  @Test
  public void testIsEmpty_true() {
    System.out.println("isEmpty");

    Collection c = new ArrayList();

    boolean result = QlackCollectionUtils.isEmpty(c);
    assertEquals(true, result);
  }

}