package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public final class DefineClassOp implements Vop {
   private final String klassName;

   public DefineClassOp(final String klassName) {
      this.klassName = klassName;
   }

   // TODO[tim]: optimise checking if class is defined - preferable using linker
   @Override public void eval(final StackFrame stackFrame, final Heap heap, final Statics statics) {
      if (!statics.isDefined(klassName)) {
         statics.defineClass(klassName);
      }
   }
}