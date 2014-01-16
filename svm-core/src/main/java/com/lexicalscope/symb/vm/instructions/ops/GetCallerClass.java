package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SMethodDescriptor;

public class GetCallerClass implements Vop {
   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      // TODO[tim]: demeter
      final StackFrame callingFrame = stack.previousFrame();
      final SMethodDescriptor callingFrameContext = (SMethodDescriptor) callingFrame.context();
      stackFrame.push(statics.whereMyClassAt(callingFrameContext.klassName()));
   }

   @Override public String toString() {
      return "CallerClass";
   }
}
