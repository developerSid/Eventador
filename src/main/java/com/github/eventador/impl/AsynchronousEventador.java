package com.github.eventador.impl;

import java.util.concurrent.Executor;

import com.github.eventador.Eventador;
import com.github.eventador.SourcingWriter;
import com.google.common.eventbus.AsyncEventBus;

/**
 * Asynchronous implementation of the {@link Eventador} that will pass the events that are published to a provided {@link Executor}. This will cause each subscriber method to be called and processed
 * in their own thread within the bounds of the configured {@link Executor} 
 * @author developer.sid@gmail.com
 *
 */
public class AsynchronousEventador extends SynchronousEventador
{
   /**
    * Creates an {@link Eventador} with the provided {@link Executor}
    * @param executor the {@link Executor} implementation that will be used to route events throughout the system
    */
   public AsynchronousEventador(Executor executor)
   {
      this(executor, new NullSourcing());
   }
   /**
    * Creates an {@link Eventador} that will route events to the provided {@link SourcingWriter} using the provided {@link Executor}
    * @param executor the {@link Executor} that will be used to execute each event passing to the subscribers
    * @param sourcing the {@link SourcingWriter} that will be used to log event to
    */
   public AsynchronousEventador(Executor executor, SourcingWriter sourcing)
   {
      super(sourcing, new AsyncEventBus(executor));
   }
   /**
    * Creates an {@link Eventador} with the provided identifier, will route events to the provided {@link SourcingWriter} using the provided {@link Executor}
    * @param executor {@link Executor} that will be used to execute each event as they are passed to the configured subscribers 
    * @param sourcing the {@link SourcingWriter} that will be used to log the event to 
    * @param identifier the name that will be given to the underlying event bus implementation
    */
   public AsynchronousEventador(Executor executor, SourcingWriter sourcing, String identifier)
   {
      super(sourcing, new AsyncEventBus(identifier, executor));
   }
}
