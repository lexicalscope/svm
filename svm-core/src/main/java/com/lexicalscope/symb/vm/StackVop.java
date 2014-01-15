package com.lexicalscope.symb.vm;

public interface StackVop {
   void eval(StackFrame top, Stack stack);
}
