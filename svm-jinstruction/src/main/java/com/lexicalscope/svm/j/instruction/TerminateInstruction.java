package com.lexicalscope.svm.j.instruction;

import static com.lexicalscope.symb.vm.j.InstructionCode.methodexit;

import com.lexicalscope.symb.vm.TerminationException;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.InstructionCode;
import com.lexicalscope.symb.vm.j.State;



public class TerminateInstruction implements Instruction {
   private Instruction prev;

   @Override public void eval(final State ctx) {
      // TODO[tim]: demeter
      throw new TerminationException();
   }

   @Override public Instruction nextIs(final Instruction instruction) {
      throw new IllegalStateException("TERMINATE has no successor");
   }

   @Override public void jmpTarget(final Instruction instruction) {
      throw new UnsupportedOperationException();
   }

   @Override public boolean hasNext() {
		return false;
   }

   @Override public Instruction next() {
      throw new IllegalStateException("TERMINATE has no successor");
   }

   @Override public Instruction jmpTarget() {
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

   @Override public InstructionCode code() {
      return methodexit;
   }

   @Override public void prevIs(final Instruction instruction) {
      assert prev == null;
      this.prev = instruction;
   }
}
