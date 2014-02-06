package com.lexicalscope.svm.j.instruction.concrete.object;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class AddressToHashCodeOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push(ctx.hashCode(ctx.pop()));
   }

   @Override public String toString() {
      return "ADDRESS TO HASHCODE";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.nativ3();
   }
}
