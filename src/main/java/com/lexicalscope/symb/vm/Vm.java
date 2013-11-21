package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.TerminationException;

public class Vm {
   private final SClassLoader classLoader = new SClassLoader();

   public State execute(final String name) {
      return execute(State.initial(name));
   }

   public State execute(State state) {
      try
      {
         while(true) {
            System.out.println(state);
            final Instruction instruction = state.instruction();
            state = instruction.eval(this, state);
         }
      } catch (final TerminationException termination) {
         return termination.getFinalState();
      }
   }

   public SClass loadClass(final String name) {
      return classLoader.load(name);
   }
}
