package com.github.eventador

import java.util.concurrent.Executors

import spock.lang.Specification

import com.github.eventador.impl.AsynchronousEventador


class TestAsynchronousEventador extends Specification
{
   def "test eventador with sourcing no listeners" ()
   {
      setup:
         def executor=Executors.newFixedThreadPool(1)
         def event=Mock(Serializable)
         def sourcing=Mock(AsynchronousSouringWriterTester)
         def eventador=new AsynchronousEventador(executor, sourcing)
      when:
         eventador.publish(event)
         sourcing.waitForSource()
      then:
         notThrown Exception
         1 * sourcing.source(_)
      cleanup:
         eventador?.destroy()
         executor?.shutdown()
   }
   def "test eventador with single listener" ()
   {
      setup:
         def executor=Executors.newFixedThreadPool(1)
         def listener=Spy(AsynchronousEventObjectTestListener, constructor:[1])
         def sourcing=Mock(AsynchronousSouringWriterTester)
         def event=new EventObjectTest("64E2CA86-42BD-47AF-8516-659ED97A0AFB")
         def eventador=new AsynchronousEventador(executor, sourcing)
      when:
         eventador.subscribe(listener)
         eventador.publish(event)
         sourcing.waitForSource()
         listener.waitForProcess()
      then:
         1 * listener.process(_)
         event == listener.events[0]
      cleanup:
         eventador?.destroy()
         executor?.shutdown()
   }
   def "test eventador with sourcing and 2 listeners" ()
   {
      setup:
         def executor=Executors.newFixedThreadPool(2)
         def listener1=new AsynchronousEventObjectTestListener(1)
         def listener2=new AsynchronousEventObjectTestListener(1)
         def sourcing=new AsynchronousSouringWriterTester(1)
         def eventador=new AsynchronousEventador(executor, sourcing)
      when:
         eventador.subscribe(listener1)
         eventador.subscribe(listener2)
         eventador.publish(new EventObjectTest("64E2CA86-42BD-47AF-8516-659ED97A0AFB"))
         sourcing.waitForSource()
         listener1.waitForProcess()
         listener2.waitForProcess()
      then:
         1 == listener1.events.size()
         1 == listener2.events.size()
      cleanup:
         eventador?.destroy()
         executor?.shutdown()
   }
   def "test subscribe and unsubscribe" ()
   {
      setup:
         def executor=Executors.newFixedThreadPool(2)
         def listener1=new AsynchronousEventObjectTestListener(2)
         def listener2=new AsynchronousEventObjectTestListener(3)
         def sourcing=new AsynchronousSouringWriterTester(3)
         def event0=new EventObjectTest()
         def event1=new EventObjectTest()
         def event2=new EventObjectTest()
         def eventador=new AsynchronousEventador(executor, sourcing)
      when:
         eventador.subscribe(listener1)
         eventador.subscribe(listener2)
         eventador.publish(event0)
         eventador.unsubscribe(listener1)
         eventador.publish(event1)
         eventador.subscribe(listener1)
         eventador.publish(event2)
         listener1.waitForProcess()
         listener2.waitForProcess()
         sourcing.waitForSource()
      then:
         2 == listener1.events.size()
         3 == listener2.events.size()
         event0 == listener1.events[0]
         event2 == listener1.events[1]
         event0 == listener2.events[0]
         event1 == listener2.events[1]
         event2 == listener2.events[2]
      cleanup:
         eventador?.destroy()
         executor?.shutdown()
   }
}
