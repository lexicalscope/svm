package com.lexicalscope.symb.vm.matchers;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Statics;

final class PeekOperandOp implements Op<Object> {
   @Override
   public Object eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      return stackFrame.peek();
   }
}