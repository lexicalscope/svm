package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class Return implements Instruction {
   private final int returnCount;

   public Return(final int returnCount) {
      this.returnCount = returnCount;
   }

   @Override
   public State eval(final Vm vm, final State state) {
      return state.discardTop(returnCount);
   }
}
