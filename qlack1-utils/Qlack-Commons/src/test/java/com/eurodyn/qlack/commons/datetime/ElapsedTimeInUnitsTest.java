package com.eurodyn.qlack.commons.datetime;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author European Dynamics S.A.
 */
public class ElapsedTimeInUnitsTest {

  public ElapsedTimeInUnitsTest() {
  }

  /**
   * Test of getDays method, of class ElapsedTimeInUnits.
   */
  @Test
  public void testGetDays_long_long() {
    System.out.println("getDays");

    long timestamp1 = (new GregorianCalendar(2012, 5, 12, 20, 38)).getTimeInMillis();
    long timestamp2 = (new GregorianCalendar(2012, 5, 14, 10, 10)).getTimeInMillis();
    int expResult = 2;

    int result = ElapsedTimeInUnits.getDays(timestamp1, timestamp2);
    assertEquals(expResult, result);
  }

  /**
   * Test of getDays method, of class ElapsedTimeInUnits.
   */
  @Test
  public void testGetDays_Calendar_Calendar() {
    System.out.println("getDays");

    Calendar g1 = new GregorianCalendar(2012, 5, 12, 20, 38);
    Calendar g2 = new GregorianCalendar(2012, 5, 15, 10, 10);
    int expResult = 3;

    int result = ElapsedTimeInUnits.getDays(g1, g2);
    assertEquals(expResult, result);
  }

}
