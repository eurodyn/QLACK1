package com.eurodyn.qlack.fuse.modules.al.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Pk implements Serializable {

  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @Column(name = "lang", nullable = false, updatable = false)
  private String lang;

  public Pk() {
  }

  public Pk(String id, String lang) {
    this.id = id;
    this.lang = lang;
  }

  public String getId() {
    return id;
  }

  public String getLang() {
    return lang;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pk)) {
      return false;
    }
    Pk pk = (Pk) o;
    return Objects.equals(getId(), pk.getId()) && Objects
        .equals(getLang(), pk.getLang());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getLang());
  }
}
