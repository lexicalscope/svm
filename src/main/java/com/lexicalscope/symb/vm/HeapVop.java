package com.lexicalscope.symb.vm;


public interface HeapVop {
   void eval(StackFrame stackFrame, Heap heap);
}
