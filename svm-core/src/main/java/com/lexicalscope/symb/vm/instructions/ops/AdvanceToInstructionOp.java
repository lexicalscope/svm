package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

final class AdvanceToInstructionOp implements Vop {
   private final InstructionNode instruction;

   AdvanceToInstructionOp(final InstructionNode instruction) {
      this.instruction = instruction;
   }

   @Override public void eval(final StateImpl ctx) {
      ctx.advanceTo(instruction);
   }
}