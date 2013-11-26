package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.concinstructions.ops.IAddOp;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;

public class ConcInstructionFactory implements InstructionFactory {
	@Override public IAddOp addOperation() {
		return new IAddOp();
	}
}
