package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.vm.j.InstructionCode.synthetic;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentation;
import com.lexicalscope.svm.j.instruction.instrumentation.InstrumentationContext;
import com.lexicalscope.symb.vm.j.InstructionCode;
import com.lexicalscope.symb.vm.j.State;

public class TraceMethodCalls implements Instrumentation {
   private final Matcher<? super State> matcher;

   public TraceMethodCalls(final Matcher<? super State> matcher) {
      this.matcher = matcher;
   }

   public static TraceMethodCalls methodCallsAt(final Matcher<? super State> matcher) {
      return new TraceMethodCalls(matcher);
   }

   @Override public void before(
         final InstructionCode code,
         final InstrumentationContext context,
         final InstructionSink sink) { }

   @Override public void after(
         final InstructionCode code,
         final InstrumentationContext context,
         final InstructionSink sink) {
      sink.linearOp(new TraceMethodCallOp(matcher), synthetic);
   }
}
