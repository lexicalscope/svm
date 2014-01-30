package com.lexicalscope.svm.j.instruction.factory;


public final class BaseInstructions implements Instructions {
   private final InstructionSource instructionSource;

   public BaseInstructions(final InstructionFactory instructionFactory) {
      this.instructionSource = new BaseInstructionSource(instructionFactory);
   }

   @Override public InstructionSource source() {
      return instructionSource;
   }
}
