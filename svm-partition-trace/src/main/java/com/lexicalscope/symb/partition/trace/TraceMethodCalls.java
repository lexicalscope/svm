package com.lexicalscope.symb.partition.trace;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentation;

public class TraceMethodCalls implements Instrumentation {
   @Override public void before(final InstructionSink sink) { }

   @Override public void after(final InstructionSink sink) {
      sink.linearOp(new TraceMethodCallOp());
   }
}
