package com.lexicalscope.symb.vm.instructions.ops;

public interface BinaryOperator {
	Object eval(Object left, Object right);
}
