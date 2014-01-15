package com.lexicalscope.symb.vm;

public interface StackOp<T> {
   T eval(StackFrame top, Stack stack);
}
