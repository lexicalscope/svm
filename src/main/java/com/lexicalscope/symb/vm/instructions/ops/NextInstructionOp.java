package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;

final class NextInstructionOp implements StackFrameVop {
   private final Instruction instruction;

   NextInstructionOp(final Instruction instruction) {
      this.instruction = instruction;
   }

   @Override
   public void eval(final StackFrame stackFrame) {
      stackFrame.advance(instruction.next());
   }
}