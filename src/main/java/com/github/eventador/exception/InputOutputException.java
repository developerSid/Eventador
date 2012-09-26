package com.github.eventador.exception;

public class InputOutputException extends EventadorException
{
   private static final long serialVersionUID = 4592307528209773631L;

   public InputOutputException()
   {
      super();
   }
   public InputOutputException(String message)
   {
      super(message);
   }
   public InputOutputException(Throwable cause)
   {
      super(cause);
   }
   public InputOutputException(String message, Throwable cause)
   {
      super(message, cause);
   }
}
