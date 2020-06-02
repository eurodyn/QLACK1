package com.eurodyn.qlack.commons.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author European Dynamics SA
 */
public class ArraysHelper {

  /**
   * Useful to convert an array to a list when Arrays.asList does not properly keep the underlying data-type (usual case
   * when you try to add an array in a JPA-query in the 'in' clause).
   */
  public static <T> List<T> arrayToList(T[] a) {
    List retVal = new ArrayList(a.length);
    retVal.addAll(Arrays.asList(a));

    return retVal;
  }

  /**
   * Convert a byte array to a list of (Bytes).
   *
   * @return List
   */
  public static List<Byte> arrayToList(byte[] a) {
    List<Byte> retVal = new ArrayList(a.length);
    for (byte i : a) {
      retVal.add(i);
    }

    return retVal;
  }


}
