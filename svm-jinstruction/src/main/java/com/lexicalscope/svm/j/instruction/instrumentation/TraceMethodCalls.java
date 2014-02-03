package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;

public class TraceMethodCalls implements Instrumentation {
   @Override public void before(final InstructionSink sink) {
      // TODO Auto-generated method stub
   }

   @Override public void after(final InstructionSink sink) {
      sink.linearOp(new TraceMethodCallOp());
   }
}
