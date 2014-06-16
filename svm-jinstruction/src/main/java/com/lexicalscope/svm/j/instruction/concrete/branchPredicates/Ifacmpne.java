package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;

import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.j.instruction.concrete.branch.BranchPredicate;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;

public class Ifacmpne implements BranchPredicate {
   @Override public Boolean eval(final JState ctx) {
      final Object value2 = ctx.pop();
      final Object value1 = ctx.pop();

      assert value1 instanceof ObjectRef && value2 instanceof ObjectRef;
      return !value1.equals(value2);
   }

   @Override public String toString() {
      return "IF_ACMPNE";
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.ifacmpne();
   }
}
