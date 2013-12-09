package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClassLoader;

/**
 * A none leaf node, which should have one or two successors
 *
 * @author tim
 */
public class InstructionInternalNode implements InstructionNode {
   private final Instruction instructionTransform;
   private final SClassLoader classLoader;
   private InstructionNode next;
   private InstructionNode target;

   public InstructionInternalNode(
         final SClassLoader classLoader,
         final Instruction instructionTransform,
         final InstructionNode prev) {
      this.instructionTransform = instructionTransform;
      this.classLoader = classLoader;

      next = new TerminateInstruction();
      target = new TerminateInstruction();

      if(prev != null) prev.next(this);
   }

   @Override public void eval(final Vm vm, final State state) {
      assert next != null;

      instructionTransform.eval(classLoader, vm, state, this);
   }

   @Override public void next(final InstructionNode instruction) {
      next = instruction;
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
      return String.format("%s", instructionTransform.toString(), next);
   }
}
