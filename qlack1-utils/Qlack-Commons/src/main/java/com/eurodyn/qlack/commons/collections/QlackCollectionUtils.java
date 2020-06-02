package com.eurodyn.qlack.commons.collections;

import java.util.Collection;

/**
 * @author European Dynamics
 */
public class QlackCollectionUtils {

  /**
   * Returns true if this collection Collection is not null and contains no elements.
   *
   * @param c - the collection
   * @return boolean - true or false
   */
  public static boolean isEmpty(Collection c) {
    return ((c != null) && (c.isEmpty()));
  }

}