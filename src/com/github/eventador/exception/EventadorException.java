package com.github.eventador.exception;

public class EventadorException extends RuntimeException
{
   private static final long serialVersionUID = -5953797880854974682L;

   public EventadorException()
   {
      super();
   }
   public EventadorException(String message)
   {
      super(message);
   }
   public EventadorException(Throwable cause)
   {
      super(cause);
   }
   public EventadorException(String message, Throwable cause)
   {
      super(message, cause);
   }
}
