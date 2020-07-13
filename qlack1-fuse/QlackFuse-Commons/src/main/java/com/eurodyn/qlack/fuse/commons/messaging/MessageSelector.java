package com.eurodyn.qlack.fuse.commons.messaging;

import java.util.ArrayList;
import java.util.List;

/**
 * @author European Dynamics SA
 */
public class MessageSelector {

  /**
   * The message selectors (pairs of property:value)
   */
  private final List<String> selectors = new ArrayList<>();


  /**
   * Add a message selector.
   *
   * @return String - the new selector concatenated (propertyName:propertyValue)
   */
  public String addSelector(String propertyName, String propertyValue) {
    String newSelector = propertyName + ":" + propertyValue;
    selectors.add(newSelector);

    return newSelector;
  }

  /**
   * Return the selectors in JSON format.
   *
   * @return String
   */
  public String toJSON() {
    StringBuilder retVal = new StringBuilder("{");
    for (int i = 0; i < selectors.size(); i++) {
      retVal.append(selectors.get(i));
      if (i < selectors.size() - 1) {
        retVal.append(", ");
      }
    }
    retVal.append("}");

    return retVal.toString();
  }
}
