package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.instructions.MethodCallInstruction.createInvokeStatic;
import static com.lexicalscope.symb.vm.instructions.ops.Ops.loadConstants;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import com.lexicalscope.heap.FastHeap;
import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.TerminationException;
import com.lexicalscope.symb.vm.natives.DefaultNativeMethods;

public class Vm {
   private final Deque<State> pending = new ArrayDeque<>();
   private final Deque<State> finished = new ArrayDeque<>();

   public Vm(final State state) {
      pending.push(state);
   }

   public State execute() {
      while (!pending.isEmpty()) {
         try {
            //System.out.println(pending.peek());
            pending.peek().advance(this);
         } catch (final TerminationException termination) {
            assert pending.peek() == termination.getFinalState();
            finished.push(pending.pop());
            System.out.println("BACKTRACK");
            System.out.println("    TERM " + finished.peek().getMeta());

         }
         catch (final RuntimeException e) {
            System.out.println(pending.peek().trace());
            throw e;
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
         System.out.println("     " + state.getMeta());
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
      final SClassLoader classLoader = new AsmSClassLoader(instructionFactory, DefaultNativeMethods.natives());
      return new Vm(Vm.initial(classLoader, entryPoint).op(loadConstants(args)));
   }

   public static State initial(final SClassLoader classLoader, final MethodInfo info) {
      return initial(classLoader, new SMethodName(info.klass(), info.name(), info.desc()));
   }

   private static State initial(final SClassLoader classLoader, final SMethodName methodName) {
      final InstructionNode defineClassClass = classLoader.defineBootstrapClassesInstruction();
      final InstructionNode initThread = classLoader.initThreadInstruction();
      final InstructionNode entryPointInstruction = new InstructionInternalNode(createInvokeStatic(methodName));

      defineClassClass.next(initThread).next(entryPointInstruction);

      final StaticsImpl statics = new StaticsImpl(classLoader);

      final DequeStack stack = new DequeStack();
      stack.push(new SnapshotableStackFrame(null, defineClassClass, 0, methodName.argSize()));
      return new StateImpl(statics, stack, new CheckingHeap(new FastHeap()), classLoader.initialMeta());
   }
}
