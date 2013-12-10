package com.lexicalscope.symb.vm;


public interface Vop {
   void eval(StackFrame stackFrame, Heap heap, Statics statics);
}
