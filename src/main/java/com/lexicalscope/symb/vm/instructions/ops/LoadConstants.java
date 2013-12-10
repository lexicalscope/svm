package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.StackFrame;

public final class LoadConstants implements Vop {
   private final Object[] values;

   LoadConstants(final Object... values) {
      this.values = values;
   }

   @Override public void eval(final StackFrame stackFrame, Stack stack, final Heap heap, Statics statics) {
      for (final Object param : values) {
         stackFrame.loadConst(param);
      }
   }
}