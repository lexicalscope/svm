package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class Load implements Vop {
   private final int var;

   public Load(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("LOAD %d", var);
   }

   @Override public void eval(final StackFrame stackFrame, Stack stack, final Heap heap, Statics statics) {
      stackFrame.push(stackFrame.local(var));
   }
}
