package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class UnaryOp implements Vop {
   private final UnaryOperator operator;

   public UnaryOp(final UnaryOperator operator) {
      this.operator = operator;
   }

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      final Object val = stackFrame.pop();

      stackFrame.push(operator.eval(val));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
