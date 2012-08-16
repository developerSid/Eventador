package com.github.eventador.impl;

import java.io.Serializable;

import com.github.eventador.SourcingWriter;

/**
 * Event sourcing implementation that does nothing with the events that are requested to be sourced.
 * @author developer.sid@gmail.com
 *
 */
public class NullSourcing implements SourcingWriter
{
   @Override
   public void source(Serializable event)
   {

   }
   @Override
   public void destroy()
   {
      
   }
}
