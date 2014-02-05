package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface Instrumentation2 {
   Instruction instrument(SMethodDescriptor method, Instruction methodEntry);
}
