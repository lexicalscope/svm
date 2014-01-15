package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class Nullary2Op implements Vop {
   private final Nullary2Operator operator;

   public Nullary2Op(final Nullary2Operator operator) {
      this.operator = operator;
   }

   @Override
   public String toString() {
      return operator.toString();
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      stackFrame.pushDoubleWord(operator.eval());
   }
}
