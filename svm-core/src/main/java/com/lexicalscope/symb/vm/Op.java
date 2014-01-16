package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;


public interface Op<T> {
   T eval(Vm<State> vm, Statics statics, Heap heap, Stack stack, StackFrame stackFrame);
}
