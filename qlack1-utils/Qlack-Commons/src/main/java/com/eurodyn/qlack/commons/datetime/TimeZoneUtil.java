package com.eurodyn.qlack.commons.datetime;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeZoneUtil {

  private TimeZoneUtil() {
  }

  public static long getUTCTimestamp() {
    return Calendar.getInstance().getTimeInMillis() - TimeZone.getDefault()
        .getOffset(Calendar.getInstance().getTimeInMillis());
  }

}
