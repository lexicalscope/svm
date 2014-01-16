package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class ACmpNe implements BranchPredicate {
   @Override public Boolean eval(Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      final Object value2 = stackFrame.pop();
      final Object value1 = stackFrame.pop();

      return !value1.equals(value2);
   }

   @Override public String toString() {
      return "IF_ACMPNE";
   }
}
