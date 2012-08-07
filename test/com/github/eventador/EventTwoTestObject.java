package com.github.eventador;

import java.util.UUID;

public class EventTwoTestObject extends BaseTestEvent implements Event
{
   private static final long serialVersionUID=4683642860344999311L;
   private static final UUID identifier=UUID.fromString("7B4838C3-ACE2-4818-BEC4-A65B187A4CB3");

   @Override
   public UUID getIdentifier()
   {
      return identifier;
   }
}
