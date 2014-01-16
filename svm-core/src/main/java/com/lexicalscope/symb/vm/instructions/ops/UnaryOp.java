package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class UnaryOp implements Vop {
   private final UnaryOperator operator;

   public UnaryOp(final UnaryOperator operator) {
      this.operator = operator;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object val = stackFrame.pop();

      stackFrame.push(operator.eval(val));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
