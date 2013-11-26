package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Stack;

public interface StackVop {
	void eval(Stack stack);
}
