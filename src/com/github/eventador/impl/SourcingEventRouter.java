package com.github.eventador.impl;

import com.github.eventador.Event;
import com.github.eventador.SourcingWriter;
import com.google.common.eventbus.Subscribe;

class SourcingEventRouter
{
   private SourcingWriter sourcing;
   
   SourcingEventRouter(SourcingWriter sourcing)
   {
      this.sourcing=sourcing;
   }
   @Subscribe public void event(Event event)
   {
      sourcing.source(event);
   }
   public SourcingWriter getSourcing()
   {
      return sourcing;
   }
}
