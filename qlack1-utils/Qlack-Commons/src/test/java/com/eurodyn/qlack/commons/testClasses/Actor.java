package com.eurodyn.qlack.commons.testClasses;

import java.util.List;

/**
 * @author European Dynamics S.A.
 */
public class Actor extends Person {

  private List movieCredits;

  private long worth;

  public Actor() {

  }

  public List getMovieCredits() {
    return this.movieCredits;
  }

  public void setMovieCredits(List movieCredits) {
    this.movieCredits = movieCredits;
  }

  public long getWorth() {
    return this.worth;
  }

  public void setWorth(long worth) {
    this.worth = worth;
  }

}
