package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public final class Terminate implements Instruction {
   @Override
   public State eval(final Vm vm, final State state) {
      throw new TerminationException(state);
   }

   @Override
   public boolean equals(final Object obj) {
      return obj != null && obj.getClass().equals(this.getClass());
   }

   @Override
   public int hashCode() {
      return this.getClass().hashCode();
   }

   @Override
   public String toString() {
      return "Terminate";
   }
}
