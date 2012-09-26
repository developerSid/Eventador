package com.github.eventador.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eventador.SourcingWriter;
import com.github.eventador.spi.BaseSourcing;

/**
 * Simple implementation that will log events the application configured logging if it has been configured.
 * @author developer.sid@gmail.com
 *
 */
public class LoggerSourcing extends BaseSourcing implements SourcingWriter
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
