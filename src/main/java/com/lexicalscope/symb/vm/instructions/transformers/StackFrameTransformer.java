package com.lexicalscope.symb.vm.instructions.transformers;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.StateTransformer;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public class StackFrameTransformer implements StateTransformer {
   private final StackFrameVop op;

   public StackFrameTransformer(final StackFrameVop op) {
      this.op = op;
   }

   @Override
   public void transform(final State state, final Instruction nextInstruction) {
      state.op(new StackFrameVop() {
         @Override
         public void eval(final StackFrame stackFrame) {
            stackFrame.advance(nextInstruction);
            op.eval(stackFrame);
         }
      });
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
