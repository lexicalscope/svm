package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class IincOp implements Vop {
   private final int var;
   private final int incr;

   public IincOp(final int var, final int incr) {
      this.var = var;
      this.incr = incr;
   }

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      stackFrame.local(var, (int)stackFrame.local(var) + incr);
   }

   @Override public String toString() {
      return String.format("IINC %d %d", var, incr);
   }
}
