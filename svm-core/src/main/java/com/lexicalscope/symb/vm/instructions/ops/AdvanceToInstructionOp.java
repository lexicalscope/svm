package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

final class AdvanceToInstructionOp implements Vop {
   private final Instruction instruction;

   AdvanceToInstructionOp(final Instruction instruction) {
      this.instruction = instruction;
   }

   @Override public void eval(final State ctx) {
      ctx.advanceTo(instruction);
   }
}