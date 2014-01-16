package com.lexicalscope.symb.stack;


public interface StackOp<T> {
   T eval(StackFrame top, Stack stack);
}
