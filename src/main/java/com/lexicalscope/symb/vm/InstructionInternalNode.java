package com.lexicalscope.symb.vm;


/**
 * A none leaf node, which should have one or two successors
 *
 * @author tim
 */
public class InstructionInternalNode implements InstructionNode {
   private final Instruction instruction;
   private InstructionNode next;
   private InstructionNode target;

   public InstructionInternalNode(final Instruction instruction) {
      this.instruction = instruction;

      next = new TerminateInstruction();
      target = new TerminateInstruction();
   }

   @Override public void eval(final Vm vm, final State state) {
      assert next != null;

      instruction.eval(vm, state, this);
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
      return String.format("%s", instruction.toString(), next);
   }
}
