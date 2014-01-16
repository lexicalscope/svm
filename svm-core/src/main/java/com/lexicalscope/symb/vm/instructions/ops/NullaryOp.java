package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class NullaryOp implements Vop {
	private final NullaryOperator operator;

	public NullaryOp(final NullaryOperator operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return operator.toString();
	}

   @Override public void eval(Vm vm, Statics statics, final Heap heap, Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      stackFrame.push(operator.eval());
   }
}
