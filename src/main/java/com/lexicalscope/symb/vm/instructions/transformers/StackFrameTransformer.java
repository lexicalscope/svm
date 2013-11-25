package com.lexicalscope.symb.vm.instructions.transformers;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.instructions.StateTransformer;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class StackFrameTransformer implements StateTransformer {
   private final StackFrameOp op;

   public StackFrameTransformer(final StackFrameOp op) {
      this.op = op;
   }

   @Override
   public State transform(final State state, final Instruction nextInstruction) {
      return state.op(new StackFrameOp() {
		@Override
		public void eval(StackFrame stackFrame) {
			stackFrame.advance(nextInstruction);
			op.eval(stackFrame);
		}
	});
   }
}
