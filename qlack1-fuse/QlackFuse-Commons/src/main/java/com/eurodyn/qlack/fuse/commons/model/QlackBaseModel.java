package com.eurodyn.qlack.fuse.commons.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Superclass that contains common fields for the Qlack entities.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@MappedSuperclass
public abstract class QlackBaseModel implements Serializable {

  /**
   * the auto-generated uuid of the entity
   */
  private String id;
  @Id
  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
