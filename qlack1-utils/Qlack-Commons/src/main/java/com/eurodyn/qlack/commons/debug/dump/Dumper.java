package com.eurodyn.qlack.commons.debug.dump;

import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author European Dynamics
 */
public class Dumper {

  private Dumper() {
  }

  /**
   * Return the contents of a List dumped in a String, comma separated.
   *
   * @param l - the list
   * @return String of the list contents.
   */
  public static String dumpList(List l) {
    StringBuilder retVal = new StringBuilder();

    if (l != null) {
      Iterator i = l.iterator();
      while (i.hasNext()) {
        String nextVal = i.next().toString();
        retVal.append(nextVal);
        if (i.hasNext()) {
          retVal.append(", ");
        }
      }
    } else {
      retVal.append("Provided List was null!");
    }

    return retVal.toString();
  }

  /**
   * Return the contents of a Set, dumped in a String, comma separated.
   *
   * @param s - the Set
   * @return String of the Set contents.
   */
  public static String dumpSet(Set s) {
    StringBuilder retVal = new StringBuilder();

    if (s != null) {
      Iterator i = s.iterator();
      while (i.hasNext()) {
        Object nextVal = i.next();
        retVal.append((nextVal != null) ? nextVal.toString() : "NULL");
        if (i.hasNext()) {
          retVal.append(", ");
        }
      }
    } else {
      retVal.append("Provided Set was null!");
    }

    return retVal.toString();
  }

  /**
   * Return the contents of a Map, dumped in a String.
   *
   * @param <T> - the class implementation of the Map interface
   * @param m - the Map
   * @return String of the Map contents.
   */
  public static <T> String dumpMap(Map<String, T> m) {
    StringBuilder retVal = new StringBuilder();
    if (m != null) {
      for (Iterator<String> i = m.keySet().iterator(); i.hasNext(); ) {
        String key = i.next();
        if (!StringUtils.isEmpty(key)) {
          retVal.append(key);
          retVal.append("=");
          retVal.append(m.get(key));
          retVal.append("\n");
        } else {
          retVal.append("Key value was empty.\n");
        }
      }
    } else {
      retVal.append("Provided Map was null.");
    }

    return retVal.toString();
  }

}
