package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.Instruction;

public interface Instrumentation {
   Instruction instrument(InstructionSource instructions, Instruction methodEntry);
}
