package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.StackFrame;

final class NextInstructionOp implements StackFrameVop {
   private final InstructionNode instruction;

   NextInstructionOp(final InstructionNode instruction) {
      this.instruction = instruction;
   }

   @Override
   public void eval(final StackFrame stackFrame) {
      stackFrame.advance(instruction.next());
   }
}