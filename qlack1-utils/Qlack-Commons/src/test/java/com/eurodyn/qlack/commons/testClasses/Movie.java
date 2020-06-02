package com.eurodyn.qlack.commons.testClasses;

/**
 * @author European Dynamics S.A.
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author European Dynamics S.A.
 */
public class Movie {

  private Date dateOfRelease;

  private String title;

  private Person director;

  private List actors;

  private String[] keywords;

  private Map genre;

  public Movie() {

  }

  public Date getDateOfRelease() {
    return this.dateOfRelease;
  }

  public void setDateOfRelease(Date dateOfRelease) {
    this.dateOfRelease = dateOfRelease;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Person getDirector() {
    return this.director;
  }

  public void setDirector(Person director) {
    this.director = director;
  }

  public List getActors() {
    return this.actors;
  }

  public void setActors(List actors) {
    this.actors = actors;
  }

  public String[] getKeywords() {
    return this.keywords;
  }

  public void setKeyWords(String[] keywords) {
    this.keywords = keywords;
  }

  public Map getGenre() {
    return this.genre;
  }

  public void setGenre(Map genre) {
    this.genre = genre;
  }

}
