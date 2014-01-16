package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class Binary2Op implements Vop {
   private final Binary2Operator operator;

   public Binary2Op(final Binary2Operator operator) {
      this.operator = operator;
   }

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      final Object right = stackFrame.popDoubleWord();
      final Object left = stackFrame.popDoubleWord();

      stackFrame.pushDoubleWord(operator.eval(left, right));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
