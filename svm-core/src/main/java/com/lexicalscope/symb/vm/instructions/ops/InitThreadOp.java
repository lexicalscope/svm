package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public class InitThreadOp implements Vop {
   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
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
