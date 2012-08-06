package com.github.eventador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.eventador.impl.SimpleRollingFileJournalSourcing;

public class TestSimpleRollingFileJournalSourcing
{
   private Path tempDir;
   private SourcingWriter sourcing;
   
   @Before
   public void before() throws IOException
   {
      this.tempDir=Files.createTempDirectory("JOURNAL");
      this.sourcing=new SimpleRollingFileJournalSourcing(tempDir, TimeUnit.MINUTES);
   }
   @After
   public void after() throws IOException
   {
      this.sourcing.destroy();
      FileUtils.deleteDirectory(tempDir.toFile());
   }
   @Test
   public void testSourcing()
   {
      
   }
}
