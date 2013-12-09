package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.instructions.ops.StackVop;

public class ReturnInstruction implements Instruction {
   private final int returnCount;

   public ReturnInstruction(final int returnCount) {
      this.returnCount = returnCount;
   }

   @Override public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      state.op(new StackVop() {
         @Override public void eval(final Stack stack) {
            stack.popFrame(returnCount);
         }
      });
   }

   @Override public String toString() {
      return String.format("RETURN %s", returnCount);
   }
}
