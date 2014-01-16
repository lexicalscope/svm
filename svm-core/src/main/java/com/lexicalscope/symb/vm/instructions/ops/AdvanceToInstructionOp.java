package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

final class AdvanceToInstructionOp implements Vop {
   private final InstructionNode instruction;

   AdvanceToInstructionOp(final InstructionNode instruction) {
      this.instruction = instruction;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      stackFrame.advance(instruction);
   }
}