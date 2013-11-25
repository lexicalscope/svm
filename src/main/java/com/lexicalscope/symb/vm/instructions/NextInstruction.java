package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.Instructions.instructionFor;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class NextInstruction implements Transistion {
   @Override public State next(final Vm vm, final State state, final AbstractInsnNode abstractInsnNode) {
      return state.op(new StackFrameOp() {
		@Override
		public void eval(StackFrame stackFrame) {
			stackFrame.advance(instructionFor(abstractInsnNode.getNext()));
			
		}
	});
   }
}
