package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public class InitThreadOp implements Vop {
   @Override public void eval(final State ctx) {
      final SClass threadClass = ctx.load(JavaConstants.THREAD_CLASS);
      final Object address = ctx.newObject(threadClass);
      ctx.put(address, SClass.OBJECT_MARKER_OFFSET, threadClass);
      ctx.currentThreadIs(address);
      ctx.push(address);
   }

   @Override public String toString() {
      return "INIT_THREAD";
   }
}
