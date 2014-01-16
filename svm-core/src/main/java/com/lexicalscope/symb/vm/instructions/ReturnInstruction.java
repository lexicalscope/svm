package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class ReturnInstruction implements Instruction {
   private final int returnCount;

   public ReturnInstruction(final int returnCount) {
      this.returnCount = returnCount;
   }

   @Override public void eval(final Vm<State> vm, final State state, final InstructionNode instruction) {
      state.op(new Vop() {
         @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
            stack.popFrame(returnCount);
         }
      }, null);
   }

   @Override public String toString() {
      return String.format("RETURN %s", returnCount);
   }
}
