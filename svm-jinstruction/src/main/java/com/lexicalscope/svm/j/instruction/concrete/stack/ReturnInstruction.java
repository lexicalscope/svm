package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class ReturnInstruction implements Vop {
   private final int returnCount;
   private final SMethodDescriptor methodName;

   public ReturnInstruction(final SMethodDescriptor methodName, final int returnCount) {
      this.methodName = methodName;
      this.returnCount = returnCount;
   }

   @Override public void eval(final JState ctx) {
      ctx.popFrame(returnCount);
   }

   @Override public String toString() {
      return String.format("RETURN %s", returnCount);
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.r3turn(methodName, returnCount);
   }
}
