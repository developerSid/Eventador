package com.github.eventador;


public interface Eventador
{
   public void subscribe(Object subscriber);
   public void unsubscribe(Object subscriber);
   public void publish(Event event);
}
