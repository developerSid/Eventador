package com.github.eventador

import spock.lang.Specification

import com.github.eventador.impl.SyncronousEventador


class TestSyncronousEventador extends Specification
{
   private Eventador eventador;
   def sourcing
   
   def setup()
   {
      sourcing=Mock(SourcingWriter);
      eventador=new SyncronousEventador(sourcing)
   }
   
   def "test eventador with sourcing no listeners" ()
   {
      
   }
}
