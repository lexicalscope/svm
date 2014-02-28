package com.lexicalscope.svm.j.instruction.concrete.object;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class AConstNullOp implements Vop {
   @Override public void eval(final JState ctx) {
      ctx.push(ctx.nullPointer());
   }

   @Override public String toString() {
      return "ACONST_NULL";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.aconst_null();
   }
}
