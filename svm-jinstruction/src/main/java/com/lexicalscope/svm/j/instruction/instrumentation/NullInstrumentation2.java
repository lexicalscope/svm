package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class NullInstrumentation2 implements Instrumentation {
   @Override public Instruction instrument(final SMethodDescriptor method, final Instruction methodEntry) {
      return methodEntry;
   }
}
