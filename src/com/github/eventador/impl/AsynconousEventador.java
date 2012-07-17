package com.github.eventador.impl;

import java.util.concurrent.Executor;

import com.github.eventador.Sourcing;
import com.google.common.eventbus.AsyncEventBus;

public class AsynconousEventador extends SyncronousEventador
{
   public AsynconousEventador(Sourcing sourcing, Executor executor)
   {
      super(sourcing, new AsyncEventBus(executor));
   }
   public AsynconousEventador(Executor executor, Sourcing sourcing, String identifier)
   {
      super(sourcing, new AsyncEventBus(identifier, executor));
   }
}
