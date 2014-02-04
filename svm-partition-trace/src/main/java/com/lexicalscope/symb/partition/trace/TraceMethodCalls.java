package com.lexicalscope.symb.partition.trace;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentation;
import com.lexicalscope.symb.vm.j.State;

public class TraceMethodCalls implements Instrumentation {
   private final Matcher<? super State> matcher;

   public TraceMethodCalls(final Matcher<? super State> matcher) {
      this.matcher = matcher;
   }

   @Override public void before(final InstructionSink sink) { }

   @Override public void after(final InstructionSink sink) {
      sink.linearOp(new TraceMethodCallOp(matcher));
   }
}
