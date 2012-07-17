package com.github.eventador.examples.spring;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSpringConfigrationEvent
{
   private AnnotationConfigApplicationContext ac;
   
   @Before
   public void before()
   {
      ac=new AnnotationConfigApplicationContext(Configuration.class);
   }
   public void test()
   {
      
   }
   @After
   public void after()
   {
      ac.close();
   }
}
