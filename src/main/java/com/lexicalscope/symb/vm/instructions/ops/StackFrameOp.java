package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.MutableStackFrame;


public interface StackFrameOp {
   void eval(MutableStackFrame stackFrame);
}
