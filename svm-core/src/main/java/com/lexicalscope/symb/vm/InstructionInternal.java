package com.lexicalscope.symb.vm;



/**
 * A none leaf node, which should have one or two successors
 *
 * @author tim
 */
public class InstructionInternal implements Instruction {
   private static TerminateInstruction terminate = new TerminateInstruction();

   private final Vop instruction;
   private Instruction next;
   private Instruction target;

   public InstructionInternal(final Vop instruction) {
      this.instruction = instruction;

      next = terminate;
      target = terminate;
   }

   @Override public void eval(final State ctx) {
      assert next != null;

      instruction.eval(ctx);
   }

   @Override public Instruction nextIs(final Instruction instruction) {
      if(next.equals(terminate)) {
         return next = instruction;
      } else {
         return next.nextIs(instruction);
      }
   }

   @Override public Instruction next() {
      return next;
   }

   @Override public Instruction jmpTarget() {
      return target;
   }

   @Override public void jmpTarget(final Instruction instruction) {
      target = instruction;
   }

   @Override public String toString() {
      return String.format("%s", instruction.toString(), next);
   }
}