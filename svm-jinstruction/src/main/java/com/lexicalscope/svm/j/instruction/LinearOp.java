package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class LinearOp implements Vop {
   private final Vop op;

   public LinearOp(final Vop op) {
      this.op = op;
   }

   @Override public void eval(final JState ctx) {
      ctx.advanceToNextInstruction();
      op.eval(ctx);
   }

   @Override
   public String toString() {
      return op.toString();
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return op.query(instructionQuery);
   }
}
