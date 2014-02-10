package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public interface Instrumentation {
   Instruction instrument(SMethodDescriptor method, Instruction methodEntry);
}
