package com.eurodyn.qlack.fuse.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO class for Attribute
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDTO implements Serializable {

  private transient Map<String, Object> attribute = new HashMap<>();

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