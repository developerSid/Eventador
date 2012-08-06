package com.github.eventador.util;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class TestTimeUtils
{
   private static final long time=1343853779245l; //3:42ish August 1 2012
   
   @Test
   public void test_roundToNextWholeUnit_Minutes()
   {
      long time=TimeUtils.roundToNextWholeUnit(TimeUnit.MINUTES, TestTimeUtils.time);
      
      Assert.assertEquals(1343853780000l, time);
   }
   @Test
   public void test_roundToNextWholeUnit_Hours()
   {
      long time=TimeUtils.roundToNextWholeUnit(TimeUnit.HOURS, TestTimeUtils.time);
      
      Assert.assertEquals(1343857320000l, time);
   }
   @Test
   public void test_roundToNextWholeUnit_Days()
   {
      long time=TimeUtils.roundToNextWholeUnit(TimeUnit.DAYS, TestTimeUtils.time);
      
      Assert.assertEquals(1343940120000l, time);
   }
   @Test
   public void test_formatNextWholeUnit_Minutes()
   {
      String formattedString=TimeUtils.formatWholeUnit(TimeUnit.MINUTES, new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SS"), TestTimeUtils.time);
      
      Assert.assertEquals("08/01/2012 15:42:00:00", formattedString);
   }
   @Test
   public void test_formatNextWholeUnit_Hours()
   {
      String formattedString=TimeUtils.formatWholeUnit(TimeUnit.HOURS, new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SS"), TestTimeUtils.time);
      
      Assert.assertEquals("08/01/2012 15:00:00:00", formattedString);
   }
   @Test
   public void test_formatNextWholeUnit_Days()
   {
      String formattedString=TimeUtils.formatWholeUnit(TimeUnit.DAYS, new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SS"), TestTimeUtils.time);
      
      Assert.assertEquals("08/01/2012 00:00:00:00", formattedString);
   }
}
