package com.github.eventador;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

import com.github.eventador.exception.UnableToSourceException;

class AsynchronousSouringWriterTester implements SourcingWriter
{
   private CountDownLatch latch;
   
   public AsynchronousSouringWriterTester()
   {
      this(1)
   }
   public AsynchronousSouringWriterTester(int events)
   {
      this.latch=new CountDownLatch(events)
   }
   @Override
   public void source(Serializable event) throws UnableToSourceException
   {
      latch.countDown()
   }
   @Override
   public void destroy()
   {

   }
   public void waitForSource()
   {
      latch.await()
   }
}
