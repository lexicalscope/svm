package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class MethodEntryVop implements Vop {
   @Override public void eval(final JState ctx) {
      // nop
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.methodentry();
   }
}
