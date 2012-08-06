package com.github.eventador.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.github.eventador.Event;
import com.github.eventador.SourcingWriter;
import com.github.eventador.exception.InputOutputException;
import com.github.eventador.exception.UnableToSourceException;
import com.github.eventador.util.TimeUtils;

public class SimpleRollingFileJournalSourcing implements SourcingWriter
{
   private static SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss:SS");
   private final Charset ascii;
   private final Path journalDir;
   private SeekableByteChannel journalChannel;
   private final TimeUnit unit;
   private volatile long nextJournalRoll;
   private Semaphore journalAccessor;  //TODO need to investigate a noncontentious way of doing this to speed up performance 
   
   public SimpleRollingFileJournalSourcing(Path journalDir, TimeUnit unit) throws InputOutputException
   {
      long currentTime=System.currentTimeMillis();
      
      this.journalDir=journalDir;
      this.unit=unit;
      this.ascii=Charset.forName("US-ASCII");  //TODO hex numbers don't need to be anything other than ASCII, but dates might.
      this.nextJournalRoll=TimeUtils.roundToNextWholeUnit(unit, currentTime);
      this.journalAccessor=new Semaphore(1, true);
      
      if(this.journalDir.toFile().isDirectory() == false)
      {
         throw new InputOutputException(this.journalDir.toString() + " is not a directory");
      }
      else
      {
         this.journalChannel=openJournal(currentTime);
      }
   }
   private SeekableByteChannel openJournal(long time) throws InputOutputException
   {
      try
      {
         String timeName=TimeUtils.formatWholeUnit(unit, format, time);
         
         return Files.newByteChannel(journalDir.resolve(Paths.get(timeName + ".journal")), EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND));
      }
      catch(FileNotFoundException e)
      {
         throw new InputOutputException(e);
      }
      catch(IOException e)
      {
         throw new InputOutputException(e);
      }
   }
   @Override
   public void source(Event event) throws UnableToSourceException
   {
      try
      {
         journalAccessor.acquire();
         
         try
         {
            long currentTime=System.currentTimeMillis();
            
            writeSource(journalChannel, event);
            
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
   private void writeSource(SeekableByteChannel journalChannel, Event event) throws UnableToSourceException
   {
      try
      {
         StringBuilder eventString=new StringBuilder('[');
         
         eventString.append(format.format(new Date()));
         eventString.append("] ");
         eventString.append(Base64.encodeBase64String(SerializationUtils.serialize(event))).append('\n');
         
         journalChannel.write(ascii.encode(CharBuffer.wrap(eventString)));
      }
      catch(IOException e)
      {
         throw new UnableToSourceException("Unable to source event " + event, e);
      }
   }
   @Override
   public void destroy()
   {
      try
      {
         journalChannel.close();
      }
      catch(IOException e)
      {
         throw new InputOutputException("Unalbe to close journal", e);
      }
   }
}
