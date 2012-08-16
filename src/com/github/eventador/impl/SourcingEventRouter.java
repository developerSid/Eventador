package com.github.eventador.impl;

import java.io.Serializable;

import com.github.eventador.SourcingWriter;
import com.google.common.eventbus.Subscribe;

class SourcingEventRouter
{
   private SourcingWriter sourcing;
   
   SourcingEventRouter(SourcingWriter sourcing)
   {
      this.sourcing=sourcing;
   }
   @Subscribe public void event(Serializable event)
   {
      sourcing.source(event);
   }
   public SourcingWriter getSourcing()
   {
      return sourcing;
   }
}
