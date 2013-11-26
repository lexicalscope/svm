package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public interface InstructionFactory {
	BinaryOperator addOperation();
}
