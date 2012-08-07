package com.github.eventador;

import java.io.Serializable;
import java.util.UUID;

public interface Event extends Serializable
{
   /**
    * Returns a unique message identifier for an instance of the {@link Event}.  It should be unique from all other instances of Event's in the system
    * @return a {@link UUID} that is unique to this instance of the implementing event
    */
   public UUID getIdentifier();
}
