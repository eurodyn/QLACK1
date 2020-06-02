package com.eurodyn.qlack.commons.arrays;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.List;

/**
 * @author European Dynamics S.A.
 */
public class ArraysHelperTest {

  public ArraysHelperTest() {

  }

  /**
   * Test of arrayToList method, of class ArraysHelper.
   */
  @Test
  public void testArrayToList_GenericType() {
    System.out.println("arrayToList");

    String[] ar = {"One", "Two", "Three", "Four", "Five"};

    List<String> list = ArraysHelper.arrayToList(ar);

    assertTrue(list.contains("One"));
    assertTrue(list.contains("Two"));
    assertTrue(list.contains("Three"));
    assertTrue(list.contains("Four"));
    assertTrue(list.contains("Five"));
  }

  /**
   * Test of arrayToList method, of class ArraysHelper.
   */
  @Test
  public void testArrayToList_byteArr() {
    System.out.println("arrayToList");

    byte[] a = {(byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6};

    List<Byte> result = ArraysHelper.arrayToList(a);

    assertTrue(result.contains((byte) 1));
    assertTrue(result.contains((byte) 2));
    assertTrue(result.contains((byte) 3));
    assertTrue(result.contains((byte) 4));
    assertTrue(result.contains((byte) 5));
    assertTrue(result.contains((byte) 6));
  }

}