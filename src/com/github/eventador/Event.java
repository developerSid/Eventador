package com.github.eventador;

import java.io.Serializable;
import java.util.UUID;

public interface Event extends Serializable
{
   public UUID getIdentifier();
}
