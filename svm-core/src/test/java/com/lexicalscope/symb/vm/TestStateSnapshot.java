package com.lexicalscope.symb.vm;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.state.Snapshotable;

public class TestStateSnapshot {
   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

   @Test
   public void snapshotOfStateCopiesEverythingStateful() {
      @SuppressWarnings("unchecked")
      final Vm<State> vm = context.mock(Vm.class, "Vm");

      final Statics statics = context.mock(Statics.class, "Statics");
      final Statics staticsCopy = context.mock(Statics.class, "Statics copy");

      final Stack stack = context.mock(Stack.class, "Stack");
      final Stack stackCopy = context.mock(Stack.class, "Stack copy");

      final Heap heap = context.mock(Heap.class, "Heap");
      final Heap heapCopy = context.mock(Heap.class, "Heap copy");

      final Snapshotable<?> meta = context.mock(Snapshotable.class, "Meta");
      final Snapshotable<?> metaCopy = context.mock(Snapshotable.class, "Meta copy");

      context.checking(new Expectations(){{
         oneOf(statics).snapshot(); will(returnValue(staticsCopy));
         oneOf(heap).snapshot(); will(returnValue(heapCopy));
         oneOf(stack).snapshot(); will(returnValue(stackCopy));
         oneOf(meta).snapshot(); will(returnValue(metaCopy));
      }});

      assertThat(new StateImpl(vm, statics, stack, heap, meta).snapshot(), equalTo(new StateImpl(vm, staticsCopy, stackCopy, heapCopy, metaCopy)));
   }
}
