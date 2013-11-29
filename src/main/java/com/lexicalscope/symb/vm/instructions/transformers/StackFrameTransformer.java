package com.lexicalscope.symb.vm.instructions.transformers;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.StateTransformer;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public final class StackFrameTransformer implements StateTransformer {
   private final StackFrameVop op;

   public StackFrameTransformer(final StackFrameVop op) {
      this.op = op;
   }

   @Override
   public void transform(final State state) {
      state.op(op);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
