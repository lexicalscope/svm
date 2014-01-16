package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.InstructionNode;

final class NextInstructionOp implements Vop {
   private final InstructionNode instruction;

   NextInstructionOp(final InstructionNode instruction) {
      this.instruction = instruction;
   }

   @Override public void eval(final StackFrame stackFrame, Stack stack, final Heap heap, Statics statics) {
      stackFrame.advance(instruction.next());
   }
}