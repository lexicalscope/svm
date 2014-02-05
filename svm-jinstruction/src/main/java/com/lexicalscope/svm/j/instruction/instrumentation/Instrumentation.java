package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;

public interface Instrumentation {
   void before(InstructionCode code, InstructionSink sink);
   void after(InstructionCode code, InstructionSink sink);
}
