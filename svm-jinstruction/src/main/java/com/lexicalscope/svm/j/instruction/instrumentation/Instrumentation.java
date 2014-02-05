package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface Instrumentation {
   Instruction instrument(SMethodDescriptor method, Instruction methodEntry);
}
