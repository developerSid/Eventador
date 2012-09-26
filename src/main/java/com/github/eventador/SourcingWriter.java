package com.github.eventador;

import java.io.Serializable;

import com.github.eventador.exception.UnableToSourceException;

/**
 * Implemenations of this class are to provide a system for processing events and logging them to an external source.  This is an attempt to be an event sourcing implementation.
 * @author developer.sid@gmail.com
 *
 */
public interface SourcingWriter
{
   /**
    * Write the event to the underlying implementation
    * @param event the event that needs to be written
    * @throws UnableToSourceException if some kind of error occurs during the attempt to write the event
    */
   public void source(Serializable event) throws UnableToSourceException;
   /**
    * Invoked to do any required cleanup when the system is being shut down.
    */
   public void destroy();
}
