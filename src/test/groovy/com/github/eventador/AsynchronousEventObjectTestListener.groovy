package com.github.eventador;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.common.eventbus.Subscribe;


public class AsynchronousEventObjectTestListener
{
   private CountDownLatch latch;
   private List<EventObjectTest> events;
   
   public AsynchronousEventObjectTestListener()
   {
      this(1)
   }
   public AsynchronousEventObjectTestListener(int events)
   {
      this.events=new LinkedList<>();
      this.latch=new CountDownLatch(events);
   }
   @Subscribe public void process(EventObjectTest event)
   {
      events.add(event);
      latch.countDown();
   }
   public List<EventObjectTest> getEvents()
   {
      return events;
   }
   public void waitForProcess()
   {
      latch.await();
   }
}
