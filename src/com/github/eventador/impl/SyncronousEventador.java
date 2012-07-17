package com.github.eventador.impl;

import com.github.eventador.Event;
import com.github.eventador.Eventador;
import com.github.eventador.Sourcing;
import com.google.common.eventbus.EventBus;

public class SyncronousEventador implements Eventador
{
   private EventBus bus;
   private SourcingEventRouter router;
   
   public SyncronousEventador(Sourcing sourcing)
   {
      this(sourcing, new EventBus());
   }
   public SyncronousEventador(Sourcing sourcing, String identifier)
   {
      this(sourcing, new EventBus(identifier));
   }
   protected SyncronousEventador(Sourcing sourcing, EventBus bus)
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
