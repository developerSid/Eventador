package com.github.eventador;

import java.util.UUID;

public class EventOneTestObject extends BaseTestEvent implements Event
{
   private static final UUID identifier=UUID.fromString("64E2CA86-42BD-47AF-8516-659ED97A0AFB");
   private static final long serialVersionUID=4683642860344999311L;

   @Override
   public UUID getIdentifier()
   {
      return identifier;
   }
}
