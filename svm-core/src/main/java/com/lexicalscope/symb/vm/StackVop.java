package com.lexicalscope.symb.vm;


public interface StackVop {
	void eval(Stack stack, Statics statics);
}
