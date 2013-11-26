package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Stack;

public interface StackOp<T> {
	T eval(Stack stack);
}
