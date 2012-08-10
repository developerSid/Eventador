package com.github.eventador;

import java.util.UUID;

public class EventObjectTest implements Event
{
   private static final long serialVersionUID=3037713647589696612L;
   private UUID identifier;
   
   public EventObjectTest(String identifier)
   {
      this.identifier=UUID.fromString(identifier);
   }
   @Override
   public UUID getIdentifier()
   {
      return identifier;
   }
   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof Event)
      {
         return ((Event)obj).getIdentifier().equals(getIdentifier());
      }
      else
      {
         return false;
      }
   }
}
