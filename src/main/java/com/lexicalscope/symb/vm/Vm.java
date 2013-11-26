package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.concinstructions.InvokeStatic;
import com.lexicalscope.symb.vm.concinstructions.TerminationException;

public class Vm {
   private final SClassLoader classLoader = new SClassLoader();

   public State execute(final String klass) {
      return execute(initial(klass));
   }

   public State execute(final State state) {
      try
      {
         while(true) {
            System.out.println(state);
            state.advance(this);
         }
      } catch (final TerminationException termination) {
         return termination.getFinalState();
      }
   }

   public SClass loadClass(final String name) {
      return classLoader.load(name);
   }


   public State initial(final String klass) {
      return initial(klass, "main", "([Ljava/lang/String;)V");
   }

   public State initial(final String klass, final String name, final String desc) {
      final SMethod method = loadMethod(klass, name, desc);
      return new State(new Stack(new InvokeStatic(klass, name, desc), 0, method.argSize()), new Heap());
   }

   public SMethod loadMethod(final String klass, final String name, final String desc) {
      final SClass targetClass = loadClass(klass);
      final SMethod targetMethod = targetClass.staticMethod(name, desc);
      return targetMethod;
   }
}
