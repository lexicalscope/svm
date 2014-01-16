package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.InstructionNode;

final class NextInstructionOp implements Vop {
   private final InstructionNode instruction;

   NextInstructionOp(final InstructionNode instruction) {
      this.instruction = instruction;
   }

   @Override public void eval(Vm vm, Statics statics, final Heap heap, Stack stack, final StackFrame stackFrame) {
      stackFrame.advance(instruction.next());
   }
}