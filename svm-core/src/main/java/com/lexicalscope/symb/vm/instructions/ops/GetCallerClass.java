package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.SMethodDescriptor;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class GetCallerClass implements Vop {
   @Override public void eval(final State ctx) {
      // TODO[tim]: demeter
      final StackFrame callingFrame = ctx.previousFrame();
      final SMethodDescriptor callingFrameContext = (SMethodDescriptor) callingFrame.context();
      ctx.push(ctx.whereMyClassAt(callingFrameContext.klassName()));
   }

   @Override public String toString() {
      return "CallerClass";
   }
}
