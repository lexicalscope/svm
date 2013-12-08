package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class DefaultInstruction implements Instruction {
   private final InstructionTransform instructionTransform;
   private final SClassLoader classLoader;
   private Instruction next;

   public DefaultInstruction(
         final SClassLoader classLoader,
         final InstructionTransform instructionTransform,
         final DefaultInstruction prev) {
      this.instructionTransform = instructionTransform;
      this.classLoader = classLoader;
      if(prev != null) prev.next = this;
   }

   @Override public void eval(final Vm vm, final State state) {
      instructionTransform.eval(classLoader, vm, state);
   }
}
