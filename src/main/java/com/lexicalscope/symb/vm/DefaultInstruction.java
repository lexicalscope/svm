package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class DefaultInstruction implements Instruction {
   private final InstructionTransform instructionTransform;

   public DefaultInstruction(final InstructionTransform instructionTransform) {
      this.instructionTransform = instructionTransform;
   }

   @Override public void eval(final SClassLoader cl, final Vm vm, final State state) {
      instructionTransform.eval(cl, vm, state);
   }
}
