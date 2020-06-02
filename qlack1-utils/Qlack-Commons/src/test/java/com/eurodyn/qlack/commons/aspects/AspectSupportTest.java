package com.eurodyn.qlack.commons.aspects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.eurodyn.qlack.commons.testClasses.Actor;
import com.eurodyn.qlack.commons.testClasses.Movie;
import com.eurodyn.qlack.commons.testClasses.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author European Dynamics S.A.
 */
public class AspectSupportTest {

  public AspectSupportTest() {

  }

  /**
   * Test of getArgumentValue method, of class AspectSupport.
   */
  @Test
  public void testGetArgumentValue() {
    System.out.println("getArgumentValue");

    String argumentName = "director";
    String[] argumentNames = {"dateOfRelease", "title", "director", "actors",
        "keywords", "genre"};
    Object[] arguments = createArgumentValues();

    AspectSupport instance = new AspectSupportImpl();
    Object result = instance.getArgumentValue(argumentName, argumentNames, arguments);

    try {
      Person p = (Person) result;
      assertEquals("George Lucas", p.getName());
    } catch (Exception e) {
      fail(e.toString());
    }
  }

  private Object[] createArgumentValues() {
    Date dateOfRelease = new GregorianCalendar(2002, 3, 25).getTime();

    String title = "Star Wars: Episode II";

    // Director
    Person director = new Person();
    director.setName("George Lucas");
    director.setGender(1);
    Map director_contacts = new HashMap();
    director_contacts.put("Home", "99922255");
    director_contacts.put("Mobile", "0345543455");
    director.setContactNumber(director_contacts);

    // create the actors
    List actors = new ArrayList();
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
    actors.add(actor1);
    actors.add(actor2);

    //genre
    Map genre = new HashMap();
    genre.put("SCF", "Science-Fiction");
    genre.put("ACT", "Action");
    Object[] arguments = {dateOfRelease, title, director, actors, null, genre};

    return arguments;
  }

  public class AspectSupportImpl extends AspectSupport {

  }
}