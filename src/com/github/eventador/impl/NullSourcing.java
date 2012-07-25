package com.github.eventador.impl;

import com.github.eventador.Event;
import com.github.eventador.SourcingWriter;

/**
 * Event sourcing implementation that does nothing with the events.
 * @author developer.sid@gmail.com
 *
 */
public class NullSourcing implements SourcingWriter
{
   @Override
   public void source(Event event)
   {

   }
   @Override
   public void destroy()
   {
      
   }
}
