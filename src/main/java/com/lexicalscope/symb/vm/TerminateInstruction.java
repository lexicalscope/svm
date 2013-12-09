package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.instructions.TerminationException;

public class TerminateInstruction implements Instruction {
   @Override public void eval(final Vm vm, final State state) {
      throw new TerminationException(state);
   }

   @Override public void next(final Instruction instruction) {
      throw new IllegalStateException("TERMINATE has no successor");
   }

   @Override public void jmpTarget(final Instruction instruction) {
      throw new UnsupportedOperationException();
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
}
