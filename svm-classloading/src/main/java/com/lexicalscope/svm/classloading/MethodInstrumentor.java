package com.lexicalscope.svm.classloading;

import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public interface MethodInstrumentor {
   Instruction instrument(SMethodDescriptor name, Instruction methodEntry);
}