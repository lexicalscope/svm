package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.concinstructions.ops.IAddOp;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public class ConcInstructionFactory implements InstructionFactory {
	@Override public BinaryOperator addOperation() {
		return new IAddOp();
	}
}
