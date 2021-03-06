package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class NoOp implements Vop {
   private final String name;

   public NoOp() {
      this("NOOP");
   }

   public NoOp(final String name) {
      this.name = name;
   }

   @Override public void eval(final JState ctx) { }

   @Override public String toString() {
      return name;
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.synthetic();
   }
}
