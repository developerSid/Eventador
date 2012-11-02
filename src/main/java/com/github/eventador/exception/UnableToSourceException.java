package com.github.eventador.exception;

public class UnableToSourceException extends EventadorException
{
   private static final long serialVersionUID = -6869160367237634041L;

   public UnableToSourceException()
   {
      super();
   }
   public UnableToSourceException(String message)
   {
      super(message);
   }
   public UnableToSourceException(Throwable cause)
   {
      super(cause);
   }
   public UnableToSourceException(String message, Throwable cause)
   {
      super(message, cause);
   }
}
