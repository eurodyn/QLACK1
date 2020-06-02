package com.eurodyn.qlack.commons.arrays;

import static org.junit.Assert.assertTrue;

import com.eurodyn.qlack.commons.testClasses.Actor;
import com.eurodyn.qlack.commons.testClasses.Movie;
import com.eurodyn.qlack.commons.testClasses.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author European Dynamics S.A.
 */
public class ArrayExtractorTest {

  public ArrayExtractorTest() {

  }

  /**
   * Test of extractFromBeanArray method, of class ArrayExtractor.
   */
  @Test
  public void testExtractFromBeanArray() throws Exception {
    System.out.println("extractFromBeanArray");

    Movie movie1 = prepareBeanData1();
    Movie movie2 = prepareBeanData2();
    Object[] movieList = {movie1, movie2};
    String propertyName = "director.name";

    List<String> expResult = new ArrayList<String>();
    expResult.add("Peter Collinson");
    expResult.add("George Lucas");

    String[] result = ArrayExtractor.extractFromBeanArray(movieList, propertyName);
    for (int i = 0; i < result.length; i++) {
      assertTrue(expResult.contains(result[i]));
    }
  }

  private Movie prepareBeanData1() {
    Movie movie = new Movie();
    movie.setTitle("The Italian Job");
    movie.setDateOfRelease(new GregorianCalendar(1969, 0, 1).getTime());

    // genre
    Map genre_map = new HashMap();
    genre_map.put("THR", "Thriller");
    genre_map.put("ACT", "Action");

    movie.setGenre(genre_map);

    // Director
    Person director = new Person();
    director.setName("Peter Collinson");
    director.setGender(1);
    Map director_contacts = new HashMap();
    director_contacts.put("Home", "99922233");
    director_contacts.put("Mobile", "0343343433");
    director.setContactNumber(director_contacts);

    movie.setDirector(director);

    // create the actors
    Actor actor1 = new Actor();
    actor1.setName("Michael Caine");
    actor1.setGender(1);
    actor1.setWorth(10000000);
    List actor1_movies = new ArrayList();

    Movie movie2 = new Movie();
    movie2.setTitle("The Fourth Protocol");

    Movie movie3 = new Movie();
    movie3.setTitle("Shiner");

    actor1_movies.add(movie2);
    actor1_movies.add(movie3);

    actor1.setMovieCredits(actor1_movies);

    Actor actor2 = new Actor();
    actor2.setName("Margaret Blye");
    actor2.setGender(2);
    actor2.setWorth(20000000);

    List actors = new ArrayList();
    actors.add(actor1);
    actors.add(actor2);

    movie.setActors(actors);

    return movie;
  }

  private Movie prepareBeanData2() {
    Movie movie = new Movie();
    movie.setTitle("Star Wars: Episode II");
    movie.setDateOfRelease(new GregorianCalendar(2002, 3, 25).getTime());

    // genre
    Map genre_map = new HashMap();
    genre_map.put("SCF", "Science-Fiction");
    genre_map.put("ACT", "Action");

    movie.setGenre(genre_map);

    // Director
    Person director = new Person();
    director.setName("George Lucas");
    director.setGender(1);
    Map director_contacts = new HashMap();
    director_contacts.put("Home", "99922255");
    director_contacts.put("Mobile", "0345543455");
    director.setContactNumber(director_contacts);

    movie.setDirector(director);

    // create the actors
    Actor actor1 = new Actor();
    actor1.setName("Hayden Christensen");
    actor1.setGender(1);
    actor1.setWorth(10000000);
    List actor1_movies = new ArrayList();

    Movie movie2 = new Movie();
    movie2.setTitle("Star Wars: Episode III");

    Movie movie3 = new Movie();
    movie3.setTitle("Forever Knight,");

    actor1_movies.add(movie2);
    actor1_movies.add(movie3);

    actor1.setMovieCredits(actor1_movies);

    Actor actor2 = new Actor();
    actor2.setName("Natalie Portman");
    actor2.setGender(2);
    actor2.setWorth(20000000);

    List actors = new ArrayList();
    actors.add(actor1);
    actors.add(actor2);

    movie.setActors(actors);

    return movie;
  }


}