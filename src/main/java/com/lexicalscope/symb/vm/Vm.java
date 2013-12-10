package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.loadConstants;
import static org.objectweb.asm.Type.getArgumentsAndReturnSizes;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import com.lexicalscope.heap.FastHeap;
import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.MethodCallInstruction;
import com.lexicalscope.symb.vm.instructions.TerminationException;

public class Vm {
   private final Deque<State> pending = new ArrayDeque<>();
   private final Deque<State> finished = new ArrayDeque<>();

   public Vm(final State state) {
      pending.push(state);
   }

   public State execute() {
      while (!pending.isEmpty()) {
         try {
            System.out.println(pending.peek());
            pending.peek().advance(this);
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

   public static Vm concreteVm(final MethodInfo entryPoint, final Object ... args) {
      return vm(new ConcInstructionFactory(), entryPoint, args);
   }

   public static Vm vm(final InstructionFactory instructionFactory, final MethodInfo entryPoint, final Object ... args) {
      final SClassLoader classLoader = new AsmSClassLoader(instructionFactory);
      return new Vm(Vm.initial(classLoader, entryPoint).op(loadConstants(args)));
   }

   public static State initial(final SClassLoader classLoader, final MethodInfo info) {
      return initial(classLoader, info.klass(), info.name(), info.desc());
   }

   private static State initial(final SClassLoader classLoader, final String klass, final String name, final String desc) {
      final InstructionInternalNode initialInstruction = new InstructionInternalNode(MethodCallInstruction.createInvokeStatic(klass, name, desc));
      final int argSize = getArgumentsAndReturnSizes(desc) >> 2;

      final StaticsImpl statics = new StaticsImpl(classLoader);
      return new StateImpl(statics, new DequeStack(initialInstruction, 0, argSize), new FastHeap(), classLoader.initialMeta());
   }
}
