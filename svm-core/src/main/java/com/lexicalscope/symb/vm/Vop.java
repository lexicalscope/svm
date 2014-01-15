package com.lexicalscope.symb.vm;


public interface Vop {
   void eval(StackFrame stackFrame, Stack stack, Heap heap, Statics statics);
}
