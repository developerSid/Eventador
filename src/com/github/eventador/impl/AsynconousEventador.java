package com.github.eventador.impl;

import java.util.concurrent.Executor;

import com.github.eventador.SourcingWriter;
import com.google.common.eventbus.AsyncEventBus;

public class AsynconousEventador extends SyncronousEventador
{
   public AsynconousEventador(SourcingWriter sourcing, Executor executor)
   {
      super(sourcing, new AsyncEventBus(executor));
   }
   public AsynconousEventador(Executor executor, SourcingWriter sourcing, String identifier)
   {
      super(sourcing, new AsyncEventBus(identifier, executor));
   }
}
