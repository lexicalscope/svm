package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public class InitThreadOp implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final SClass threadClass = statics.load(JavaConstants.THREAD_CLASS);
      final Object address = heap.newObject(threadClass);
      heap.put(address, SClass.OBJECT_MARKER_OFFSET, threadClass);
      stack.currentThread(address);
      stackFrame.push(address);
   }

   @Override public String toString() {
      return "INIT_THREAD";
   }
}
