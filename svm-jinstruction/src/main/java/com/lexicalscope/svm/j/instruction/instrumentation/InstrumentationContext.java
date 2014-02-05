package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.symb.vm.j.InstructionCode;

public interface InstrumentationContext {
   void before(InstructionCode code, InstructionSink sink);
   void after(InstructionCode code, InstructionSink sink);
}