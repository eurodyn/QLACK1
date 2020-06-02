package com.eurodyn.qlack.commons.testClasses;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Map;

/**
 * @author European Dynamics S.A.
 */
public class Person {

  private String name;

  private int gender;

  private Map contactNumber;

  public Person() {

  }

  public String getName() {
    return this.name == null ? "NoName" : this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGender() {
    return this.gender;
  }

  public void setGender(int gender) {
    // 0 - not given, 1 - Male, 2 - Female
    this.gender = (gender > 2 || gender < 0) ? 0 : gender;
  }

  public Map getContactNumber() {
    return this.contactNumber;
  }

  public void setContactNumber(Map contactNumber) {
    this.contactNumber = contactNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Person)) {
      return false;
    }
    Person otherPerson = (Person) o;
    return new EqualsBuilder()
        .append(name, otherPerson.getName())
        .append(gender, otherPerson.getGender())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(7, 51)
        .append(name)
        .append(gender)
        .append(contactNumber)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("Name", name)
        .append("Gender", gender)
        .append("Contact Details", contactNumber)
        .toString();
  }

}
