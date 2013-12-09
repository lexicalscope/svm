package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionTransform;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.ops.StackOp;

public class Return implements InstructionTransform {
   private final int returnCount;

   public Return(final int returnCount) {
      this.returnCount = returnCount;
   }

   @Override
   public void eval(final SClassLoader cl, final Vm vm, final State state, final Instruction instruction) {
      state.op(new StackOp<Void>() {
		@Override
		public Void eval(final Stack stack) {
			stack.popFrame(returnCount);

			return null;
		}
	  });
   }

   @Override
	public String toString() {
		return String.format("RETURN %s", returnCount);
	}
}
