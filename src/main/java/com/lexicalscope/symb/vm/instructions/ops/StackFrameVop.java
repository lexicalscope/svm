package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public interface StackFrameVop {
   void eval(StackFrame stackFrame);
}
