package com.lexicalscope.symb.vm;



/**
 * A none leaf node, which should have one or two successors
 *
 * @author tim
 */
public class InstructionInternalNode implements InstructionNode {
   private static TerminateInstruction terminate = new TerminateInstruction();

   private final Vop instruction;
   private InstructionNode next;
   private InstructionNode target;

   public InstructionInternalNode(final Vop instruction) {
      this.instruction = instruction;

      next = terminate;
      target = terminate;
   }

   @Override public void eval(final State ctx) {
      assert next != null;

      instruction.eval(ctx);
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
