package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.heap.Heap;


public interface Op<T> {
   T eval(StackFrame stackFrame, Stack stack, Heap heap, Statics statics);
}
