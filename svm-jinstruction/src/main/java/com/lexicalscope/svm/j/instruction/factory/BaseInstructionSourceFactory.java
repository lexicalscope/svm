package com.lexicalscope.svm.j.instruction.factory;

public class BaseInstructionSourceFactory implements InstructionSourceFactory {
   @Override public InstructionSource instructionSource(final InstructionFactory instructionFactory) {
      return new BaseInstructionSource(instructionFactory);
   }
}