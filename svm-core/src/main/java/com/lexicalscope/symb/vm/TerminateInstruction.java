package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;


public class TerminateInstruction implements InstructionNode {
   @Override public void eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, final InstructionNode instructionNode) {
      throw new TerminationException(vm.state());
   }

   @Override public InstructionNode next(final InstructionNode instruction) {
      throw new IllegalStateException("TERMINATE has no successor");
   }

   @Override public void jmpTarget(final InstructionNode instruction) {
      throw new UnsupportedOperationException();
   }

   @Override public InstructionNode next() {
      throw new IllegalStateException("TERMINATE has no successor");
   }

   @Override public InstructionNode jmpTarget() {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean equals(final Object obj) {
      return obj != null && obj.getClass().equals(this.getClass());
   }

   @Override
   public int hashCode() {
      return this.getClass().hashCode();
   }

   @Override
   public String toString() {
      return "TERMINATE";
   }
}
