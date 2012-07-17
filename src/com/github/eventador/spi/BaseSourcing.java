package com.github.eventador.spi;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.SerializationUtils;

import com.github.eventador.Event;
import com.github.eventador.Sourcing;

public abstract class BaseSourcing implements Sourcing
{
   protected abstract void source(String event);
   
   @Override
   public void source(Event event)
   {
      byte bytes[]=SerializationUtils.serialize(event);
      String encoded=Base64.encodeBase64String(bytes);
      
      source(encoded);
   }
}
