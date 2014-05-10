package org.treasureboat.foundation.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.treasureboat.foundation.TBFString;

import com.webobjects.foundation.NSTimeZone;
import com.webobjects.foundation.NSTimestamp;

public class TBFTimestampUtilities {

  /** 'YYYY-mm-DD HH:MM:SS.0' */
  public static final int CONVERT_FORMAT_1 = 1;

  /** 'YYYYmmDD HHMMSS' */
  public static final int CONVERT_FORMAT_2 = 2;

  public static NSTimestamp convert(String timestampString, int format) {
    if(TBFString.stringIsNullOrEmpty(timestampString)) {
      return null;
    }

    String yyyymmddhhmmss;

    switch (format) {
    case CONVERT_FORMAT_1:
      yyyymmddhhmmss = TBFString.removeCharacters(timestampString, "- :.");
      break;

    case CONVERT_FORMAT_2:
      yyyymmddhhmmss = TBFString.removeCharacters(timestampString, "- :.");
      break;

    default:
      yyyymmddhhmmss = timestampString;
      break;
    }

    // remove milisecond
    if(yyyymmddhhmmss.length() > 14) {
      yyyymmddhhmmss = yyyymmddhhmmss.substring(0, 14);
    }

    // should be 14 lengths
    if(yyyymmddhhmmss.length() != 14) {
      return null;
    }

    String y = yyyymmddhhmmss.substring(0, 4);
    String m = yyyymmddhhmmss.substring(4, 6);
    String d = yyyymmddhhmmss.substring(6, 8);
    String hh = yyyymmddhhmmss.substring(8, 10);
    String mm = yyyymmddhhmmss.substring(10, 12);
    String ss = yyyymmddhhmmss.substring(12, 14);

    return createTimestampWithUTC(Integer.valueOf(y), Integer.valueOf(m), Integer.valueOf(d), Integer.valueOf(hh), Integer.valueOf(mm), Integer.valueOf(ss));
  }

  /**
   * create a UTC Timestamp
   * <hr />
   * 
   * タイムスタンプを作成します。(UTCタイムゾーン使用)
   * <br />
   * 
   * @param year
   * @param month
   * @param day
   * @param hours
   * @param minutes
   * @param seconds
   * 
   * @return
   *        Timestamp<br />
   *        タイムスタンプ
   */
  public static NSTimestamp createTimestampWithUTC(int year, int month, int day, int hours, int minutes, int seconds) {
    return utcDate(createTimestamp(year, month, day, hours, minutes, seconds, null));
  }

  /**
   * create a Timestamp
   * <hr />
   * 
   * タイムスタンプを作成します。
   * <br />
   * 
   * @param year
   * @param month
   * @param day
   * @param hours
   * @param minutes
   * @param seconds
   * @param tz
   * 
   * @return 
   *        Timestamp<br />
   *        タイムスタンプ
   */
  public static NSTimestamp createTimestamp(int year, int month, int day, int hours, int minutes, int seconds, NSTimeZone tz) {
    if(tz == null) {
      tz = NSTimeZone.systemTimeZone();
    }

    return new NSTimestamp(year, month, day, hours, minutes, seconds, tz);
  }

  /**
   * 現在の現地時間を変更することなく、そのままで UTC にすることです。
   * 時間が変わりませんが、タイムゾーンが変わります。
   * 
   * @param timestamp
   *        タイムスタンプ
   * 
   * @return 
   *        UTC に変換したタイムスタンプ
   */
  public static NSTimestamp utcDate(NSTimestamp timestamp) {
    GregorianCalendar gc = calendarForTimestamp(timestamp);

    int d = gc.get(GregorianCalendar.DAY_OF_MONTH);
    int m = gc.get(GregorianCalendar.MONTH) + 1;
    int y = gc.get(GregorianCalendar.YEAR);
    int h = gc.get(GregorianCalendar.HOUR_OF_DAY);
    int n = gc.get(GregorianCalendar.MINUTE);
    int s = gc.get(GregorianCalendar.SECOND);

    NSTimeZone tz = NSTimeZone.timeZoneWithName("Etc/GMT", true);
    return new NSTimestamp(y, m, d, h, n, s, tz);
  }

  /**
   * NSTimestamp を GregorianCalendar に変換します
   * 
   * @param 
   *        timestamp
   * 
   * @return 
   *        変換されている GregorianCalendar
   */
  public static GregorianCalendar calendarForTimestamp(NSTimestamp timestamp) {
    GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
    calendar.setTime(timestamp);
    return calendar;
  }

}
