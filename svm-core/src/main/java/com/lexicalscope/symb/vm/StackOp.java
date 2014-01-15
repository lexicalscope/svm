package com.lexicalscope.symb.vm;


public interface StackOp<T> {
	T eval(Stack stack);
}
