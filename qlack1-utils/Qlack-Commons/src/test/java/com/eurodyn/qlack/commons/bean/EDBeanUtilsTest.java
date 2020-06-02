package com.eurodyn.qlack.commons.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.eurodyn.qlack.commons.testClasses.Movie;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author European Dynamics S.A.
 */
public class EDBeanUtilsTest {

  public EDBeanUtilsTest() {
  }

  /**
   * Test of getPropertyFromBeanArray method, of class EDBeanUtils.
   */
  @Test
  public void testGetPropertyFromBeanArray() throws Exception {
    System.out.println("getPropertyFromBeanArray");

    Object[] beanArray = {createMovieData1(), createMovieData2()};
    String propertyName = "title";

    String[] expResult = {"The Italian Job", "Star Wars: Episode II"};

    String[] result = EDBeanUtils.getPropertyFromBeanArray(beanArray, propertyName);

    assertEquals(expResult[0], result[0]);
    assertEquals(expResult[1], result[1]);
  }

  /**
   * Test of getPropertyFromBeanArray method, of class EDBeanUtils. passed non-existing argument name and should throw
   * IllegalAccessException
   */
  @Test
  public void testGetPropertyFromBeanArray_nestedName() throws Exception {
    System.out.println("testGetPropertyFromBeanArray_nestedName");

    Object[] beanArray = {createMovieData1(), createMovieData2()};
    String propertyName = "aaa";

    try {
      String[] result = EDBeanUtils.getPropertyFromBeanArray(beanArray, propertyName);
      fail();
    } catch (NoSuchMethodException e) {
      System.out.println("Exception caught:" + e);
    }
  }

  /**
   * Test of getUniquePropertyFromBeanArray method, of class EDBeanUtils.
   */
  @Test
  public void testGetUniquePropertyFromBeanArray() throws Exception {
    System.out.println("getUniquePropertyFromBeanArray");

    Object[] beanArray = {createMovieData1(), createMovieData2(), createMovieData3()};
    String propertyName = "dateOfRelease";

    String[] result = EDBeanUtils.getUniquePropertyFromBeanArray(beanArray, propertyName);

    String expResult1 = (new GregorianCalendar(1969, 0, 1).getTime()).toString();

    assertEquals((new GregorianCalendar(1969, 0, 1).getTime()).toString(), result[0]);
    assertEquals((new GregorianCalendar(2002, 3, 25).getTime()).toString(), result[1]);

  }

  private Movie createMovieData1() {
    Movie movie = new Movie();
    movie.setTitle("The Italian Job");
    movie.setDateOfRelease(new GregorianCalendar(1969, 0, 1).getTime());

    // genre
    Map genre_map = new HashMap();
    genre_map.put("THR", "Thriller");
    genre_map.put("ACT", "Action");

    movie.setGenre(genre_map);

    return movie;
  }

  private Movie createMovieData2() {
    Movie movie = new Movie();
    movie.setTitle("Star Wars: Episode II");
    movie.setDateOfRelease(new GregorianCalendar(2002, 3, 25).getTime());

    // genre
    Map genre_map = new HashMap();
    genre_map.put("SCF", "Science-Fiction");
    genre_map.put("ACT", "Action");

    movie.setGenre(genre_map);

    return movie;
  }

  private Movie createMovieData3() {
    Movie movie = new Movie();
    movie.setTitle("Minority Report");
    movie.setDateOfRelease(new GregorianCalendar(2002, 3, 25).getTime());

    // genre
    Map genre_map = new HashMap();
    genre_map.put("CRI", "Crime");
    genre_map.put("ACT", "Action");
    genre_map.put("MYS", "Mystery");

    movie.setGenre(genre_map);

    return movie;
  }

}