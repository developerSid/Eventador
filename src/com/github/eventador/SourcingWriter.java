package com.github.eventador;

import com.github.eventador.exception.UnableToSourceException;

public interface SourcingWriter
{
   public void source(Event event) throws UnableToSourceException;
   public void destroy();
}
