package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;

public interface ArrayConstructor {
   void newArray(StackFrame stackFrame, Heap heap, Statics statics, InitStrategy initStrategy);
}
