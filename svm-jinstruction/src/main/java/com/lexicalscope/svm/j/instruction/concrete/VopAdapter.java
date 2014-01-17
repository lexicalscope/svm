package com.lexicalscope.svm.j.instruction.concrete;

import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class VopAdapter implements Vop {
   private final Op<?> op;

   public VopAdapter(final Op<?> op) {
      this.op = op;
   }

   @Override public void eval(final State ctx) {
      op.eval(ctx);
   }

   @Override public String toString() {
      return op.toString();
   }
}
