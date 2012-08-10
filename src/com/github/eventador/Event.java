package com.github.eventador;

import java.io.Serializable;
import java.util.UUID;

/**
 * A unique event that needs to be published to the {@link Eventador} implementation.
 * <br />
 * Implemenations of this class need to provide a {@link UUID} that is really only for the users tracking with the current implementation.
 * @author developer.sid@gmail.com
 *
 */
public interface Event extends Serializable
{
   /**
    * This may be removed in a future release based on it's usefulness
    * Returns a unique message identifier for an instance of the {@link Event}.  It should be unique from all other instances of Event's in the system
    * @return a {@link UUID} that is unique to this instance of the implementing event
    */
   public UUID getIdentifier();
}
