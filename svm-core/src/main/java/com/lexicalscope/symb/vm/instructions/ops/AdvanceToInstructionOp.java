package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Vop;

final class AdvanceToInstructionOp implements Vop {
   private final InstructionNode instruction;

   AdvanceToInstructionOp(final InstructionNode instruction) {
      this.instruction = instruction;
   }

   @Override public void eval(final Context ctx) {
      ctx.advanceTo(instruction);
   }
}