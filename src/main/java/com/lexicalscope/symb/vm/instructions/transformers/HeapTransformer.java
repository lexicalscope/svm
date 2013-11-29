package com.lexicalscope.symb.vm.instructions.transformers;

import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.StateTransformer;

public final class HeapTransformer implements StateTransformer {
   private final HeapVop op;

   public HeapTransformer(final HeapVop op) {
      this.op = op;
   }

   @Override
   public void transform(final State state) {
      state.op(op);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
