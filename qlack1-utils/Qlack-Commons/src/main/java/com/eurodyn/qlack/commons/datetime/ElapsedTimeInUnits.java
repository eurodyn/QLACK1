package com.eurodyn.qlack.commons.datetime;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class provides methods calculating the elapsed time between dates.
 *
 * @author European Dynamics SA
 */
public class ElapsedTimeInUnits {

  /**
   * Returns the elapsed time between two given dates in days. This method calculates the elapsed time based on calendar
   * days and not on 24hour intervals. This means that the elapsed time between 01/01/2010 23:59 and 02/01/2010 00:01 is
   * 1 day.
   *
   * @return int The elapsed time between the provided dates in days
   */
  public static int getDays(long timestamp1, long timestamp2) {
    Calendar gc1 = GregorianCalendar.getInstance();
    gc1.setTimeInMillis(timestamp1);
    Calendar gc2 = GregorianCalendar.getInstance();
    gc2.setTimeInMillis(timestamp2);

    return getDays(gc1, gc2);
  }

  /**
   * Returns the elapsed time between two given dates in days. This method calculates the elapsed time based on calendar
   * days and not on 24hour intervals. This means that the elapsed time between 01/01/2010 23:59 and 02/01/2010 00:01 is
   * 1 day.
   *
   * @return int The elapsed time between the provided dates in days
   */
  public static int getDays(Calendar g1, Calendar g2) {
    GregorianCalendar gc1, gc2;
    if (g2.after(g1)) {
      gc2 = (GregorianCalendar) g2.clone();
      gc1 = (GregorianCalendar) g1.clone();
    } else {
      gc2 = (GregorianCalendar) g1.clone();
      gc1 = (GregorianCalendar) g2.clone();
    }

    int counter = 0;
    while ((gc1.get(Calendar.YEAR) != gc2.get(Calendar.YEAR))
        || (gc1.get(Calendar.MONTH) != gc2.get(Calendar.MONTH))
        || (gc1.get(Calendar.DATE) != gc2.get(Calendar.DATE))) {
      gc1.add(Calendar.DATE, 1);
      counter++;
    }

    return counter;
  }
}
