package com.github.eventador;

import java.io.Serializable;
import java.util.UUID;

public class EventObjectTest implements Serializable
{
   private static final long serialVersionUID=3037713647589696612L;
   private UUID identifier;
   
   public EventObjectTest(String identifier)
   {
      this.identifier=UUID.fromString(identifier);
   }
   public UUID getIdentifier()
   {
      return identifier;
   }
   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof EventObjectTest)
      {
         return ((EventObjectTest)obj).getIdentifier().equals(getIdentifier());
      }
      else
      {
         return false;
      }
   }
}
