package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.svm.vm.j.klass.SMethod;

public interface InstructionFinder {
   boolean findInstruction(SMethod method);
}
