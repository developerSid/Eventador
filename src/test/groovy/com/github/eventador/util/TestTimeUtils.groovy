package com.github.eventador.util;

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

import org.junit.Assert
import org.junit.Test

import spock.lang.Specification


public class TestTimeUtils  extends Specification
{
   private static final long time=1343853779245l //3:42ish August 1 2012
   static final def simpleFormat=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SS")
   
   @Test
   public void "round to next whole TimeUnit success" ()
   {
      when:
         long timeResult=TimeUtils.roundToNextWholeUnit(unit, testTime)
      then:
         timeResult == result
      where:
         unit             | testTime | result
         TimeUnit.MINUTES | time     | 1343853780000l
         TimeUnit.HOURS   | time     | 1343857320000l
         TimeUnit.DAYS    | time     | 1343940120000l
   }
   def "format next whole TimeUnit success" ()
   {
      when:
         String formattedTime=TimeUtils.formatWholeUnit(unit, format, testTime)
      then:
         formattedTime == result
      where:
         unit             | format       | result                   | testTime
         TimeUnit.MINUTES | simpleFormat | "08/01/2012 15:42:00:00" | time
         TimeUnit.HOURS   | simpleFormat | "08/01/2012 15:00:00:00" | time
         TimeUnit.DAYS    | simpleFormat | "08/01/2012 00:00:00:00" | time
   }
   def "round to next whole number fails with -1 because of unsupported time unit" ()
   {
      when:
         long timeResult=TimeUtils.roundToNextWholeUnit(unit, testTime)
      then:
         timeResult == -1
      where:
         unit                  | testTime
         TimeUnit.NANOSECONDS  | time
         TimeUnit.MILLISECONDS | time
         TimeUnit.SECONDS      | time
         null                  | time
   }
   def "format next whole fails with null when an unsupported TimeUnit is used" ()
   {
      when:
         String formattedTime=TimeUtils.formatWholeUnit(unit, format, testTime)
      then:
         formattedTime == null
      where:
         unit                  | format       | testTime
         TimeUnit.NANOSECONDS  | simpleFormat | time 
         TimeUnit.MILLISECONDS | simpleFormat | time 
         TimeUnit.SECONDS      | simpleFormat | time 
         null                  | simpleFormat | time 
   }
}
