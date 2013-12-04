package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.loadConstants;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.TerminationException;

public class Vm {
   private final SClassLoader classLoader;
   private final Deque<State> pending = new ArrayDeque<>();
   private final Deque<State> finished = new ArrayDeque<>();

   public Vm(final State state, final SClassLoader classLoader) {
      pending.push(state);
      this.classLoader = classLoader;
   }

   public State execute() {
      while (!pending.isEmpty()) {
         try {
            System.out.println(pending.peek());
            pending.peek().advance(classLoader, this);
         } catch (final TerminationException termination) {
            assert pending.peek() == termination.getFinalState();
            finished.push(pending.pop());
            System.out.println("BACKTRACK");
         }
      }
      return result();
   }

   public State result() {
      return finished.peek();
   }

   public static Vm concreteVm(final MethodInfo entryPoint, final Object ... args) {
      return vm(new ConcInstructionFactory(), entryPoint, args);
   }

   public static Vm vm(final InstructionFactory instructionFactory, final MethodInfo entryPoint, final Object ... args) {
      final SClassLoader classLoader = new AsmSClassLoader(instructionFactory);
      return new Vm(classLoader.initial(entryPoint).op(loadConstants(args)), classLoader);
   }

   public void fork(final State[] states) {
      pending.pop();
      System.out.println("FORK");
      for (final State state : states) {
         pending.push(state);
      }
   }

   public Collection<State> results() {
      return finished;
   }
}
