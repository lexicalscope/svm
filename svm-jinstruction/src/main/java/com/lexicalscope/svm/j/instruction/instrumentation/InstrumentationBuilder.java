package com.lexicalscope.svm.j.instruction.instrumentation;

import org.hamcrest.Matcher;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class InstrumentationBuilder {
   private final ListMultimap<Matcher<? super SMethodDescriptor>, Instrumentation> instrumentationMap = LinkedListMultimap.create();

   public void instrument(final Matcher<? super SMethodDescriptor> methodMatcher, final Instrumentation instrumentation) {
      assert methodMatcher != null;
      assert instrumentation != null;
      instrumentationMap.put(methodMatcher, instrumentation);
   }

   public Instrumentation2 instrumentation2(final InstructionSource instructions) {
      if(instrumentationMap.isEmpty()) {
         return new NullInstrumentation2();
      }
      return new MultimapInstrumentationContext(instructions, instrumentationMap);
   }
}
