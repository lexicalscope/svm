package com.lexicalscope.symb.vm.instructions.transformers;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.instructions.StateTransformer;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class StackFrameTransformer implements StateTransformer {
   private final StackFrameOp<Void> op;

   public StackFrameTransformer(final StackFrameOp<Void> op) {
      this.op = op;
   }

   @Override
   public void transform(final State state, final Instruction nextInstruction) {
      state.op(new StackFrameOp<Void>() {
         @Override
         public Void eval(final StackFrame stackFrame) {
            stackFrame.advance(nextInstruction);
            op.eval(stackFrame);
            return null;
         }
      });
   }
}
