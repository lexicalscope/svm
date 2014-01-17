package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Ge implements BranchPredicate {
   @Override
   public Boolean eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      return (int) stackFrame.pop() >= 0;
   }

   @Override public Boolean eval(final Context ctx) {
      return (int) ctx.pop() >= 0;
   }

   @Override public String toString() {
      return "GE";
   }
}
