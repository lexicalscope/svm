package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public interface StackFrameOp {
   void eval(StackFrame stackFrame);
}
