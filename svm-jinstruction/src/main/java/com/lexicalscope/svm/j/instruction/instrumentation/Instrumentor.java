package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface Instrumentor {
   Instruction instrument(InstructionSource instructions, SMethodDescriptor method, Instruction methodEntry);
}
