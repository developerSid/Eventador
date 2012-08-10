package com.github.eventador;

import java.util.concurrent.TimeUnit

import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.SerializationUtils
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder

import spock.lang.Specification

import com.github.eventador.impl.SimpleRollingFileJournalSourcing


class TestSimpleRollingFileJournalSourcing extends Specification
{
   @Rule TemporaryFolder tempFolder=new TemporaryFolder()
   
   def deserializeLine(String line)
   {
      String objDef=line.split(" ")[1];
      
      return SerializationUtils.deserialize(Base64.decodeBase64(objDef))
   }
   def "source 2 events make sure they can be unsourced" ()
   {
      File tempDir=tempFolder.newFolder()
      SourcingWriter writer=new SimpleRollingFileJournalSourcing(tempDir, TimeUnit.MINUTES)
      Event one=new EventObjectTest("64E2CA86-42BD-47AF-8516-659ED97A0AFB")
      Event two=new EventObjectTest("7B4838C3-ACE2-4818-BEC4-A65B187A4CB3")
      
      when:
         writer.source(one)
         writer.source(two)
         writer.destroy()
      then:
         def lines=tempDir.listFiles()[0].readLines()
      expect:
         lines.size() == 2
         one == deserializeLine(lines.get(0))
         two == deserializeLine(lines.get(1))
   }
   def "make sure file rolls" ()
   {
      File tempDir=tempFolder.newFolder()
      SourcingWriter writer=new SimpleRollingFileJournalSourcing(tempDir, TimeUnit.SECONDS)
      Event one=new EventObjectTest("64E2CA86-42BD-47AF-8516-659ED97A0AFB")
      Event two=new EventObjectTest("7B4838C3-ACE2-4818-BEC4-A65B187A4CB3")
      Event three=new EventObjectTest("2D2A681B-7C8F-4395-82DE-9327BE7CBE82")
      
      when:
         writer.source(one);
         TimeUnit.MILLISECONDS.sleep(1200)
         writer.source(two)
         TimeUnit.MILLISECONDS.sleep(1200)
         writer.source(three);
      then:
         tempDir.listFiles().size() == 3
   }
}
