package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class LinearInstruction implements Instruction {
   private final Vop op;

   public LinearInstruction(final Vop op) {
      this.op = op;
   }

   @Override public void eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, final InstructionNode instructionNode) {
      stackFrame.advance(instructionNode.next());
      op.eval(vm, statics, heap, stack, stackFrame, instructionNode);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
