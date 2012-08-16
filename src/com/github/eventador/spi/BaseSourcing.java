package com.github.eventador.spi;

import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.SerializationUtils;

import com.github.eventador.SourcingWriter;

public abstract class BaseSourcing implements SourcingWriter
{
   protected abstract void source(String event);
   
   @Override
   public void source(Serializable event)
   {
      byte bytes[]=SerializationUtils.serialize(event);
      String encoded=Base64.encodeBase64String(bytes);
      
      source(encoded);
   }
}
