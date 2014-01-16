package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class BinaryOp implements Vop {
   private final BinaryOperator operator;

   public BinaryOp(final BinaryOperator operator) {
      this.operator = operator;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object right = stackFrame.pop();
      final Object left = stackFrame.pop();

      stackFrame.push(operator.eval(left, right));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
