package com.github.eventador;

import java.io.Serializable;

import com.google.common.eventbus.Subscribe;

/**
 * Simple interface for defining a system that will route events that are pushed to the publish method and routed to the appropriate subscribers
 * @author developer.sid@gmail.com
 *
 */
public interface Eventador
{
   /**
    * subscribe an event listener that has been annotated with the {@link Subscribe} annotation from Google's Guava Event bus implemenation
    * @param subscriber the object that will have events routed to it
    */
   public void subscribe(Object subscriber);
   /**
    * unsubscribe a subscriber from the framework
    * @param subscriber the object that will be removed from the framework
    */
   public void unsubscribe(Object subscriber);
   /**
    * publish an event to the subscribers registered in the framework
    * @param event the event to publish
    */
   public void publish(Serializable event);
}
