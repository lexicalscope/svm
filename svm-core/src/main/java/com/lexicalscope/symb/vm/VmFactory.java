package com.lexicalscope.symb.vm;

import static com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction.createInvokeStatic;

import com.lexicalscope.heap.FastHeap;
import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.factory.ConcInstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.symb.code.AsmSMethodName;
import com.lexicalscope.symb.klass.SMethodDescriptor;
import com.lexicalscope.symb.stack.SnapshotableStackFrame;
import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class VmFactory {
   static State initial(final Vm<State> vm, final SClassLoader classLoader, final SMethodDescriptor methodName, final Object[] args) {
      final Instruction defineClassClass = classLoader.defineBootstrapClassesInstruction();
      final Instruction initThread = classLoader.initThreadInstruction();
      final Instruction loadArgs = classLoader.loadArgsInstruction(args);
      final Instruction entryPointInstruction = new InstructionInternal(createInvokeStatic(methodName));

      defineClassClass.nextIs(initThread).nextIs(loadArgs).nextIs(entryPointInstruction);

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
