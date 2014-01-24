package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction.createInvokeStatic;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.factory.ConcInstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.Instructions.InstructionSink;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.symb.classloading.AsmSClassLoader;
import com.lexicalscope.symb.classloading.SClassLoader;
import com.lexicalscope.symb.classloading.StaticsImpl;
import com.lexicalscope.symb.heap.HeapFactory;
import com.lexicalscope.symb.stack.DequeStack;
import com.lexicalscope.symb.stack.SnapshotableStackFrame;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.VmImpl;
import com.lexicalscope.symb.vm.conc.checkingheap.CheckingHeapFactory;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.StateImpl;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class VmFactory {
   private static State initial(
         final Vm<State> vm,
         final HeapFactory heapFactory,
         final SClassLoader classLoader,
         final SMethodDescriptor methodName,
         final Object[] args) {
      final Instruction defineClassClass = classLoader.defineBootstrapClassesInstruction();
      final Instruction initThread = classLoader.initThreadInstruction();
      final Instruction loadArgs = classLoader.loadArgsInstruction(args);

      createInvokeStatic(methodName, new InstructionSink(){
         @Override public void nextInstruction(final Vop node) {
            defineClassClass.nextIs(initThread).nextIs(loadArgs).nextIs(new InstructionInternal(node));
         }

         @Override public void noInstruction() {
            // TODO Auto-generated method stub

         }});

      final StaticsImpl statics = new StaticsImpl(classLoader);

      final DequeStack stack = new DequeStack();
      stack.push(new SnapshotableStackFrame(null, defineClassClass, 0, methodName.argSize()));
      return new StateImpl(vm, statics, stack, heapFactory.heap(), classLoader.initialMeta());
   }

   public static State initial(
         final Vm<State> vm,
         final HeapFactory heapFactory,
         final SClassLoader classLoader,
         final MethodInfo info,
         final Object[] args) {
      return initial(vm, heapFactory, classLoader, new AsmSMethodName(info.klass(), info.name(), info.desc()), args);
   }

   private static Vm<State> vm(
         final InstructionFactory instructionFactory,
         final HeapFactory heapFactory,
         final MethodInfo entryPoint,
         final Object ... args) {
      final SClassLoader classLoader = new AsmSClassLoader(instructionFactory, DefaultNativeMethods.natives());

      final Vm<State> vm = new VmImpl<State>();
      vm.initial(initial(vm, heapFactory, classLoader, entryPoint, args));
      return vm;
   }

   public static Vm<State> concreteVm(final MethodInfo entryPoint, final Object ... args) {
      return vm(new ConcInstructionFactory(), new CheckingHeapFactory(), entryPoint, args);
   }
}
