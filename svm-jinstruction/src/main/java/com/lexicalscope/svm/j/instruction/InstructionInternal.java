package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.InstructionCode;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;



/**
 * A none leaf node, which should have one or two successors
 *
 * @author tim
 */
public class InstructionInternal implements Instruction {
   private static TerminateInstruction terminate = new TerminateInstruction();

   private final Vop instruction;
   private Instruction prev;
   private Instruction next;
   private Instruction target;

   private final InstructionCode code;


   public InstructionInternal(final Vop instruction, final InstructionCode code) {
      this.instruction = instruction;
      this.code = code;

      next = terminate;
      target = terminate;
   }

   @Override public void eval(final State ctx) {
      assert next != null;
      instruction.eval(ctx);
   }

   @Override public Instruction nextIs(final Instruction instruction) {
      if(next.equals(terminate)) {
         instruction.prevIs(this);
         return next = instruction;
      } else {
         return next.nextIs(instruction);
      }
   }

   @Override public Instruction next() {
      return next;
   }

   @Override public boolean hasNext() {
      assert next != null;
      return next != null;
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

   @Override public InstructionCode code() {
      return code;
   }

   @Override public void prevIs(final Instruction instruction) {
      assert prev == null;
      prev = instruction;
   }
}
