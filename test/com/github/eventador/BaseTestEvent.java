package com.github.eventador;

public abstract class BaseTestEvent implements Event
{
   private static final long serialVersionUID = 3037713647589696612L;

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
