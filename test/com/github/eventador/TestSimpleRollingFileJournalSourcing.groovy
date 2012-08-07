package com.github.eventador;

import java.util.concurrent.TimeUnit

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.After
import org.junit.Assert;
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder

import spock.lang.Specification

import com.github.eventador.impl.SimpleRollingFileJournalSourcing

class TestSimpleRollingFileJournalSourcing extends Specification
{
   @Rule TemporaryFolder tempFolder=new TemporaryFolder()
   
   def "test sourcing of 2 events" ()
   {
      File tempDir=tempFolder.getRoot();
      SourcingWriter writer=new SimpleRollingFileJournalSourcing(tempDir, TimeUnit.MINUTES)
      
      when:
         writer.source(new EventOneTestObject())
         writer.source(new EventTwoTestObject())
         writer.destroy()
      then:
         def lines=tempDir.listFiles()[0].readLines()
      expect:
         lines.size() == 2
   }
}
