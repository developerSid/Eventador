package com.github.eventador.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eventador.Sourcing;
import com.github.eventador.spi.BaseSourcing;

public class LoggerSourcing extends BaseSourcing implements Sourcing
{
   private Logger logger;
   
   public LoggerSourcing(String loggerName)
   {
      logger=LoggerFactory.getLogger(loggerName);
   }
   @Override
   protected void source(String event)
   {
      logger.info(event);
   }
   @Override
   public void destroy()
   {

   }
}
