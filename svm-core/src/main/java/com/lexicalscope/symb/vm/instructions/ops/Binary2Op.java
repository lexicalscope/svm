package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class Binary2Op implements Vop {
   private final Binary2Operator operator;

   public Binary2Op(final Binary2Operator operator) {
      this.operator = operator;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object right = stackFrame.popDoubleWord();
      final Object left = stackFrame.popDoubleWord();

      stackFrame.pushDoubleWord(operator.eval(left, right));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
