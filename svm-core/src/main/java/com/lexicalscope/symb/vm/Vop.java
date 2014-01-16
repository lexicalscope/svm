package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;


public interface Vop {
   void eval(StackFrame stackFrame, Stack stack, Heap heap, Statics statics);
}
