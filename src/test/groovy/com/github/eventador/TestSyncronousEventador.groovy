package com.github.eventador

import groovy.json.JsonOutput.*
import spock.lang.Specification

import com.github.eventador.impl.SynchronousEventador


class TestSyncronousEventador extends Specification
{
   def "test eventador with sourcing no listeners" ()
   {
      setup:
         def event=Mock(Serializable)
         def sourcing=Mock(SourcingWriter)
         def eventador=new SynchronousEventador(sourcing)
      when:
         eventador.publish(event)
      then:
         notThrown Exception
         1 * sourcing.source(event)
      cleanup:
         eventador?.destroy()
   }
   def "test eventador with no sourcing single listener" ()
   {
      setup:
         def listener=Spy(EventObjectTestListener, constructor:[])
         def event=new EventObjectTest("64E2CA86-42BD-47AF-8516-659ED97A0AFB")
         def eventador=new SynchronousEventador()
      when:
         eventador.subscribe(listener);
         eventador.publish(event)
      then:
         1 * listener.process(event)
         event == listener.events[0]
      cleanup:
         eventador?.destroy()
   }
   def "test eventador with sourcing and 2 listeners" ()
   {
      setup:
         def listener1=Spy(EventObjectTestListener, constructor:[])
         def listener2=Spy(EventObjectTestListener, constructor:[])
         def sourcing=Mock(SourcingWriter)
         def eventador=new SynchronousEventador(sourcing)
      when:
         eventador.subscribe(listener1)
         eventador.subscribe(listener2)
         eventador.publish(new EventObjectTest("64E2CA86-42BD-47AF-8516-659ED97A0AFB"))
      then:
         1 * listener1.process(_)
         1 * listener2.process(_)
         1 * sourcing.source(_)
      cleanup:
         eventador?.destroy()
   }
   def "test subscribe and unsubscribe" ()
   {
      setup:
         def listener1=Spy(EventObjectTestListener, constructor:[])
         def listener2=Spy(EventObjectTestListener, constructor:[])
         def event0=new EventObjectTest()
         def event1=new EventObjectTest()
         def event2=new EventObjectTest()
         def sourcing=Mock(SourcingWriter)
         def eventador=new SynchronousEventador(sourcing)
      when:
         eventador.subscribe(listener1)
         eventador.subscribe(listener2)
         eventador.publish(event0)
         eventador.unsubscribe(listener1)
         eventador.publish(event1)
         eventador.subscribe(listener1)
         eventador.publish(event2)
      then:
         2 * listener1.process(_)
         3 * listener2.process(_)
         3 * sourcing.source(_)
         event0 == listener1.events[0]
         event2 == listener1.events[1]
         event0 == listener2.events[0]
         event1 == listener2.events[1]
         event2 == listener2.events[2]
      cleanup:
         eventador?.destroy()
   }
}
