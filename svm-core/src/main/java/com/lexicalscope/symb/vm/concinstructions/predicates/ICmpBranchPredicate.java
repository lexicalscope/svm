package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class ICmpBranchPredicate implements BranchPredicate {
   private final ICmpOp op;

   public ICmpBranchPredicate(final ICmpOp op) {
      this.op = op;
   }

   @Override public Boolean eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      final int value2 = (int) stackFrame.pop();
      final int value1 = (int) stackFrame.pop();
      return op.cmp(value1, value2);
   }

   @Override public Boolean eval(final Context ctx) {
      final int value2 = (int) ctx.pop();
      final int value1 = (int) ctx.pop();
      return op.cmp(value1, value2);
   }

   @Override public String toString() {
      return op.toString();
   }
}
