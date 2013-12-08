package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.InstructionTransform;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public final class Terminate implements InstructionTransform {
   @Override
   public void eval(final SClassLoader cl, final Vm vm, final State state) {
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
      return "TERMINATE";
   }
}
