package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.heap.Heap;


public interface Vop {
   void eval(StackFrame stackFrame, Stack stack, Heap heap, Statics statics);
}
