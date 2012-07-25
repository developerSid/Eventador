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
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.SerializationUtils;

import com.github.eventador.Event;
import com.github.eventador.SourcingWriter;
import com.github.eventador.exception.InputOutputException;
import com.github.eventador.exception.UnableToSourceException;

public class SimpleFileJournalSourcing implements SourcingWriter
{
   private Charset ascii;
   private Path journalDir;
   private SeekableByteChannel journalChannel;
   private AtomicInteger lineCount=new AtomicInteger(0);
   private final int maxRecordsPerFile;
   
   public SimpleFileJournalSourcing(Path journalDir, int maxRecordsPerFile) throws InputOutputException
   {
      this.ascii=Charset.forName("US-ASCII");
      this.journalDir=journalDir;
      this.maxRecordsPerFile=maxRecordsPerFile;
      
      if(this.journalDir.toFile().isDirectory() == false)
      {
         throw new InputOutputException(this.journalDir.toString() + " is not a directory");
      }
      
      try
      {
         this.journalChannel=Files.newByteChannel(journalDir.resolve(Paths.get("journal")), EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND));
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
         String eventString=Base64.encodeBase64String(SerializationUtils.serialize(event)) + "\n";
         
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
