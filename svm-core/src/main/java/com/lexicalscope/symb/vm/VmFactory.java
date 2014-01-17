package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.instructions.MethodCallInstruction.createInvokeStatic;

import com.lexicalscope.heap.FastHeap;
import com.lexicalscope.symb.stack.SnapshotableStackFrame;
import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;
import com.lexicalscope.symb.vm.classloader.AsmSMethodName;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethodDescriptor;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.natives.DefaultNativeMethods;

public class VmFactory {
   static State initial(final Vm<State> vm, final SClassLoader classLoader, final SMethodDescriptor methodName, final Object[] args) {
      final InstructionNode defineClassClass = classLoader.defineBootstrapClassesInstruction();
      final InstructionNode initThread = classLoader.initThreadInstruction();
      final InstructionNode loadArgs = classLoader.loadArgsInstruction(args);
      final InstructionNode entryPointInstruction = new InstructionInternalNode(createInvokeStatic(methodName));

      defineClassClass.next(initThread).next(loadArgs).next(entryPointInstruction);

      final StaticsImpl statics = new StaticsImpl(classLoader);

      final DequeStack stack = new DequeStack();
      stack.push(new SnapshotableStackFrame(null, defineClassClass, 0, methodName.argSize()));
      return new StateImpl(vm, statics, stack, new CheckingHeap(new FastHeap()), classLoader.initialMeta());
   }

   public static State initial(final Vm<State> vm, final SClassLoader classLoader, final MethodInfo info, final Object[] args) {
      return initial(vm, classLoader, new AsmSMethodName(info.klass(), info.name(), info.desc()), args);
   }

   public static Vm<State> vm(final InstructionFactory instructionFactory, final MethodInfo entryPoint, final Object ... args) {
      final SClassLoader classLoader = new AsmSClassLoader(instructionFactory, DefaultNativeMethods.natives());

      final Vm<State> vm = new VmImpl<State>();
      vm.initial(initial(vm, classLoader, entryPoint, args));
      return vm;
   }

   public static Vm<State> concreteVm(final MethodInfo entryPoint, final Object ... args) {
      return vm(new ConcInstructionFactory(), entryPoint, args);
   }

}
