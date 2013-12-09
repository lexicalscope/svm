package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.StateTransformer;

public class Noop implements StateTransformer {
   private final String description;

   public Noop(final String description) {
      this.description = description;
   }

   @Override public void transform(final State state) {
      // noop
   }

   @Override public String toString() {
      return description;
   }
}
