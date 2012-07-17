package com.github.eventador.impl;

import com.github.eventador.Event;
import com.github.eventador.Sourcing;
import com.google.common.eventbus.Subscribe;

class SourcingEventRouter
{
   private Sourcing sourcing;
   
   SourcingEventRouter(Sourcing sourcing)
   {
      this.sourcing = sourcing;
   }
   @Subscribe public void event(Event event)
   {
      sourcing.source(event);
   }
   public Sourcing getSourcing()
   {
      return sourcing;
   }
}
