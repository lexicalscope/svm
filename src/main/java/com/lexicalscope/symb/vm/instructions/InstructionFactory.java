package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.concinstructions.ops.IAddOp;

public interface InstructionFactory {
	IAddOp addOperation();
}
