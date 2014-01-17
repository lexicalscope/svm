package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SMethodDescriptor;

public class GetCallerClass implements Vop {
   @Override public void eval(final Context ctx) {
      // TODO[tim]: demeter
      final StackFrame callingFrame = ctx.previousFrame();
      final SMethodDescriptor callingFrameContext = (SMethodDescriptor) callingFrame.context();
      ctx.push(ctx.whereMyClassAt(callingFrameContext.klassName()));
   }

   @Override public String toString() {
      return "CallerClass";
   }
}
