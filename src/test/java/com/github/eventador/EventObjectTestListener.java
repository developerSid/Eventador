package com.github.eventador;

import java.util.LinkedList;
import java.util.List;

import com.google.common.eventbus.Subscribe;


public class EventObjectTestListener
{
   private List<EventObjectTest> events;
   
   public EventObjectTestListener()
   {
      this.events=new LinkedList<>();
   }
   @Subscribe public void process(EventObjectTest event)
   {
      events.add(event);
   }
   public List<EventObjectTest> getEvents()
   {
      return events;
   }
}
