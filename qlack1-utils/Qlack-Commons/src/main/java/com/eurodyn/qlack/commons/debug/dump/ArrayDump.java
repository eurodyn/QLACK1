package com.eurodyn.qlack.commons.debug.dump;

/**
 * @author European Dynamics SA
 */
public class ArrayDump {

  /**
   * Display a dump of the content of an array.
   *
   * @param <T> - any class object
   * @param array - the array
   */
  public static <T> void dump(T[] array) {
    for (Object o : array) {
      System.out.println(o.toString());
    }
  }

  /**
   * Concatenate the content of an array to a String, separating each object by a delimiter.
   *
   * @param <T> - any class object
   * @param array - the array
   * @param delimiter = the separator delimiter
   * @return String
   */
  public static <T> String stringify(T[] array, String delimiter) {
    StringBuilder sb = new StringBuilder();
    for (Object o : array) {
      sb.append(o.toString());
      sb.append(delimiter);
    }

    return sb.substring(0, sb.length() - delimiter.length());
  }
}
