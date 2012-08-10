package com.github.eventador.util;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

public final class TimeUtils
{
   private TimeUtils(){/*Don't instantiate*/}
   
   /**
    * rounds an instant
    * @param unit
    * @param time
    * @return
    */
   public static long roundToNextWholeUnit(TimeUnit unit, long time)
   {
      DateTime currentTime=new DateTime(time);
      
      switch(unit)
      {
         case MINUTES:
            currentTime=currentTime.plusMinutes(1);
            break;
         case HOURS:
            currentTime=currentTime.plusHours(1);
            break;
         case DAYS:
            currentTime=currentTime.plusDays(1);
            break;
         default:
            return -1;
      }
      
      return new DateTime(currentTime.getYear(), currentTime.getMonthOfYear(), currentTime.getDayOfMonth(), currentTime.getHourOfDay(), currentTime.getMinuteOfHour(), 0, 0).getMillis();
   }
   public static String formatWholeUnit(TimeUnit unit, SimpleDateFormat format, long time)
   {
      DateTime currentTime=new DateTime(time);
      int dayOfMonth=0, hourOfDay=0, minuteOfHour=0;
      
      switch(unit)
      {
         case MINUTES:
            dayOfMonth=currentTime.getDayOfMonth();
            hourOfDay=currentTime.getHourOfDay();
            minuteOfHour=currentTime.getMinuteOfHour();
            break;
         case HOURS:
            dayOfMonth=currentTime.getDayOfMonth();
            hourOfDay=currentTime.getHourOfDay();
            minuteOfHour=0;
            break;
         case DAYS:
            dayOfMonth=currentTime.getDayOfMonth();
            hourOfDay=0;
            minuteOfHour=0;
            break;
         default:
            return null;
      }
      
      return new DateTime(currentTime.getYear(), currentTime.getMonthOfYear(), dayOfMonth, hourOfDay, minuteOfHour, 0, 0).toString(format.toPattern());
   }
}
