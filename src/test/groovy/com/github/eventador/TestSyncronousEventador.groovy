package com.github.eventador

import spock.lang.Specification

import com.github.eventador.impl.SyncronousEventador


class TestSyncronousEventador extends Specification
{
   def "test eventador with sourcing no listeners" ()
   {
      setup:
         def event=Mock(Serializable)
         def sourcing=Mock(SourcingWriter)
         def eventador=new SyncronousEventador(sourcing)
      when:
         eventador.publish(event)
      then:
         notThrown Exception
         1 * sourcing.source(_)
   }
   def "test eventador with no sourcing single listener" ()
   {
      setup:
         def listener=Spy(EventObjectTestListener, constructorArgs:[])
         def event=new EventObjectTest("64E2CA86-42BD-47AF-8516-659ED97A0AFB")
         def eventador=new SyncronousEventador()
      when:
         eventador.publish(event)
      then:
         1 * listener.process(_)
   }
}
