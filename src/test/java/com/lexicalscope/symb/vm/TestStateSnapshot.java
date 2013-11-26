package com.lexicalscope.symb.vm;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class TestStateSnapshot {
   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

   @Test
   public void test() {
      final Stack stack = context.mock(Stack.class, "Stack");
      final Stack stackCopy = context.mock(Stack.class, "Stack copy");

      final Heap heap = context.mock(Heap.class, "Heap");
      final Heap heapCopy = context.mock(Heap.class, "Heap copy");

      context.checking(new Expectations(){{
         oneOf(heap).snapshot(); will(returnValue(heapCopy));
         oneOf(stack).snapshot(); will(returnValue(stackCopy));
      }});

      assertThat(new State(stack, heap).snapshot(), equalTo(new State(stackCopy, heapCopy)));
   }
}
