package com.github.eventador.util;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

/**
 * Contains utilities for calculating some simple time formatting and trimming or truncating.  In other languages this would be done with a mixin. I couldn't come up with a better way to do it
 * so I just made a utility so it would be a little easier to test. 
 * 
 * 
 * @author developer.sid@gmail.com
 *
 */
public final class TimeUtils
{
   private TimeUtils(){/*Don't instantiate*/}
   
   /**
    * rounds an instant of time to the next whole unit specified by the {@link TimeUnit} unit parameter.
    * @param unit the {@link TimeUnit} that the caller would like the instant rounded up to. Should not be null. If it is -1 is returned
    * @param time the instant that is to be rounded up to the next whole unit
    * @return the next whole instant if it can be calculated.
    */
   public static long roundToNextWholeUnit(TimeUnit unit, long time)
   {
      if(unit != null)
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
      else
      {
         return -1;
      }
   }
   /**
    * Formats an instant to a whole unit by trimming off units below the {@link TimeUnit} unit parameter
    * @param unit the {@link TimeUnit} that the instant should be rounded to
    * @param format the format the resultant {@link String} should be in
    * @param time the instant to be rounded
    * @return a formatted {@link String} rounded to the passed in {@link TimeUnit}.  Will return null if unable to be calculated
    */
   public static String formatWholeUnit(TimeUnit unit, SimpleDateFormat format, long time)
   {
      if(unit != null && format != null)
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
      else
      {
         return null;
      }
   }
}
