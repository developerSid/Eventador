package com.github.eventador.impl;

import com.github.eventador.Event;
import com.github.eventador.Eventador;
import com.github.eventador.SourcingWriter;
import com.google.common.eventbus.EventBus;

/**
 * A synchronous implementation of {@link Eventador} that will use the thread that the even was published on to pass it to all the subscribers
 * registered within.
 * @author developer.sid@gmail.com
 *
 */
public class SyncronousEventador implements Eventador
{
   private EventBus bus;
   private SourcingEventRouter router;
   
   /**
    * Default constructor that uses a {@link NullSourcing} sourcing configuration that will not write events to a log
    */
   public SyncronousEventador()
   {
      this(new NullSourcing());
   }
   /**
    * Configures a {@link SyncronousEventador} that will write events to the provided {@link SourcingWriter} implementation
    * @param sourcing a {@link SourcingWriter} that all events that go through the system will be written
    */
   public SyncronousEventador(SourcingWriter sourcing)
   {
      this(sourcing, new EventBus());
   }
   /**
    * Configures a {@link SyncronousEventador} that will write events to the provided {@link SourcingWriter} implementation
    * @param sourcing a {@link SourcingWriter} that all events that go through the system will be written
    * @param identifier the name given to the underlying framework that routes events to the appropriate subscribers
    */
   public SyncronousEventador(SourcingWriter sourcing, String identifier)
   {
      this(sourcing, new EventBus(identifier));
   }
   protected SyncronousEventador(SourcingWriter sourcing, EventBus bus)
   {
      this.bus=bus;
      this.router=new SourcingEventRouter(sourcing);
      this.bus.register(this.router);
   }
   @Override
   public void subscribe(Object subscriber)
   {
      bus.register(subscriber);
   }
   @Override
   public void unsubscribe(Object subscriber)
   {
      bus.unregister(subscriber);
   }
   @Override
   public void publish(Event event)
   {
      bus.post(event);
   }
   public void destroy()
   {
      bus.unregister(router);
      router.getSourcing().destroy();
   }
}
