package com.eurodyn.qlack.fuse.commons.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO class for Attribute
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public class AttributeDTO implements Serializable {

  private transient Map<String, Object> attribute = new HashMap<>();

  public Map<String, Object> getAttribute() {
    return attribute;
  }

  public void setAttribute(Map<String, Object> attribute) {
    this.attribute = attribute;
  }

  /**
   * @return the attribute
   */
  public Object clearAttribute(String key) {
    return this.getAttribute().remove(key);
  }

  /**
   * @return the attribute
   */
  public Object getAttribute(String key) {
    return this.getAttribute().get(key);
  }

  /**
   *
   */
  public void setAttribute(String key, Object value) {
    this.getAttribute().put(key, value);
  }

}
