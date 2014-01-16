package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class GetCallerClass implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      // TODO[tim]: demeter
      stackFrame.push(statics.whereMyClassAt(stack.previousFrame().receiverKlass()));
   }

   @Override public String toString() {
      return "CallerClass";
   }
}
