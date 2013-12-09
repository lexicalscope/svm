package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class DefaultInstruction implements Instruction {
   private final InstructionTransform instructionTransform;
   private final SClassLoader classLoader;
   private Instruction next;
   private Instruction target;

   public DefaultInstruction(
         final SClassLoader classLoader,
         final InstructionTransform instructionTransform,
         final Instruction prev) {
      this.instructionTransform = instructionTransform;
      this.classLoader = classLoader;

      next = new TerminateInstruction();
      target = new TerminateInstruction();

      if(prev != null) prev.next(this);
   }

   @Override public void eval(final Vm vm, final State state) {
      instructionTransform.eval(classLoader, vm, state, this);
   }

   @Override public void next(final Instruction instruction) {
      next = instruction;
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
      return String.format("%s", instructionTransform.toString(), next);
   }
}
