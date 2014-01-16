package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;


/**
 * A none leaf node, which should have one or two successors
 *
 * @author tim
 */
public class InstructionInternalNode implements InstructionNode {
   private static TerminateInstruction terminate = new TerminateInstruction();

   private final Instruction instruction;
   private InstructionNode next;
   private InstructionNode target;

   public InstructionInternalNode(final Instruction instruction) {
      this.instruction = instruction;

      next = terminate;
      target = terminate;
   }

   @Override public void eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, final InstructionNode instructionNode) {
      assert next != null;

      instruction.eval(vm, statics, heap, stack, stackFrame, instructionNode);
   }

   @Override public InstructionNode next(final InstructionNode instruction) {
      if(next.equals(terminate)) {
         return next = instruction;
      } else {
         return next.next(instruction);
      }
   }

   @Override public InstructionNode next() {
      return next;
   }

   @Override public InstructionNode jmpTarget() {
      return target;
   }

   @Override public void jmpTarget(final InstructionNode instruction) {
      target = instruction;
   }

   @Override public String toString() {
      return String.format("%s", instruction.toString(), next);
   }
}
