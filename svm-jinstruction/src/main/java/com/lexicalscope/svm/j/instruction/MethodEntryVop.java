package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class MethodEntryVop implements Vop {
   private final SMethodDescriptor methodName;

   public MethodEntryVop(final SMethodDescriptor methodName) {
      this.methodName = methodName;
   }

   @Override public void eval(final JState ctx) {
      // nop
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.methodentry(methodName);
   }

   @Override public String toString() {
      return "METHOD ENTRY";
   }
}
