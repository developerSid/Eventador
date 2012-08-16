package com.github.eventador.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.github.eventador.SourcingWriter;
import com.github.eventador.exception.InputOutputException;
import com.github.eventador.exception.UnableToSourceException;
import com.github.eventador.util.TimeUtils;

public class SimpleRollingFileJournalSourcing implements SourcingWriter
{
   private static SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy-HHmmssSS");
   private final File journalDir;
   private PrintStream journalChannel;
   private final TimeUnit unit;
   private volatile long nextJournalRoll;
   private Semaphore journalAccessor;  //TODO need to investigate a noncontentious way of doing this to try and speed up performance 
   
   public SimpleRollingFileJournalSourcing(File journalDir, TimeUnit unit) throws InputOutputException
   {
      long currentTime=System.currentTimeMillis();
      
      this.journalDir=journalDir;
      this.unit=unit;
      this.nextJournalRoll=TimeUtils.roundToNextWholeUnit(unit, currentTime);
      this.journalAccessor=new Semaphore(1, true);
      
      if(this.journalDir.isDirectory() == false)
      {
         throw new InputOutputException(this.journalDir.toString() + " is not a directory");
      }
      else
      {
         this.journalChannel=openJournal(currentTime);
      }
   }
   private PrintStream openJournal(long time) throws InputOutputException
   {
      try
      {
         String timeName=TimeUtils.formatWholeUnit(unit, format, time);
         
         return new PrintStream(new File(this.journalDir, timeName + ".journal"));
      }
      catch(FileNotFoundException e)
      {
         throw new InputOutputException(e);
      }
   }
   @Override
   public void source(Serializable event) throws UnableToSourceException
   {
      try
      {
         journalAccessor.acquire();
         
         try
         {
            long currentTime=System.currentTimeMillis();
            
            writeSource(journalChannel, event, currentTime);
            
            if(currentTime >= nextJournalRoll)
            {
               nextJournalRoll=TimeUtils.roundToNextWholeUnit(unit, currentTime);
               IOUtils.closeQuietly(journalChannel);
               journalChannel=openJournal(currentTime);
            }
         }
         finally
         {
            journalAccessor.release();
         }
      }
      catch(InterruptedException e)
      {
         
      }
   }
   private void writeSource(PrintStream journalChannel, Serializable event, long currentTime) throws UnableToSourceException
   {
      journalChannel.printf("[%s] %s\n", format.format(new Date(currentTime)), Base64.encodeBase64String(SerializationUtils.serialize(event)));
   }
   @Override
   public void destroy()
   {
      try
      {
         journalAccessor.acquire();
         journalChannel.close();
         journalAccessor.release();
      }
      catch(InterruptedException e)
      {
         
      }
   }
}
