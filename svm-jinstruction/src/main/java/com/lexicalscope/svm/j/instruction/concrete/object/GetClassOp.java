package com.lexicalscope.svm.j.instruction.concrete.object;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public class GetClassOp implements Vop {
   @Override public void eval(final State ctx) {
      final Object klassFromHeap = ctx.get(ctx.pop(), SClass.OBJECT_MARKER_OFFSET);
      ctx.push(ctx.whereMyStaticsAt((SClass) klassFromHeap));
   }

   @Override public String toString() {
      return "GET CLASS";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.nativ3();
   }
}
