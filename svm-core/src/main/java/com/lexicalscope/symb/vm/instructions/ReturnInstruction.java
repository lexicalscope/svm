package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;

public class ReturnInstruction implements Instruction {
   private final int returnCount;

   public ReturnInstruction(final int returnCount) {
      this.returnCount = returnCount;
   }

   @Override public void eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, final InstructionNode instructionNode) {
      stack.popFrame(returnCount);
   }

   @Override public String toString() {
      return String.format("RETURN %s", returnCount);
   }
}
