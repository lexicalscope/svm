package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class ICmpGt implements BranchPredicate {
   @Override public Boolean eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final int value2 = (int) stackFrame.pop();
      final int value1 = (int) stackFrame.pop();
      return value1 > value2;
   }

   @Override public String toString() {
      return "ICMPGT";
   }
}
