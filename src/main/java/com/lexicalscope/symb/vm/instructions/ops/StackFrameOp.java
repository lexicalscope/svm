package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public interface StackFrameOp<T> {
   T eval(StackFrame stackFrame);
}
