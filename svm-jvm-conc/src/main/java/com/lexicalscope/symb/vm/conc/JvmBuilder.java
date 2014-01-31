package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction.invokeStatic;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.InitThreadOp;
import com.lexicalscope.svm.j.instruction.factory.AbstractInstructionSink;
import com.lexicalscope.svm.j.instruction.factory.BaseInstructionSource;
import com.lexicalscope.svm.j.instruction.factory.ConcInstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.svm.j.natives.NativeMethods;
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

public final class JvmBuilder {
   private InstructionFactory instructionFactory = new ConcInstructionFactory();
   private HeapFactory heapFactory = new CheckingHeapFactory();
   private final NativeMethods natives = DefaultNativeMethods.natives();

   public Vm<State> build() {
      return new VmImpl<State>();
   }

   public void instructionFactory(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
   }

   public SClassLoader classLoader() {
      return new AsmSClassLoader(instructionFactory, natives());
   }

   public NativeMethods natives() {
      return natives;
   }

   public BaseInstructionSource instructionSource() {
      return new BaseInstructionSource(instructionFactory);
   }

   public HeapFactory heapFactory() {
      return heapFactory;
   }

   public void heapFactory(final HeapFactory heapFactory) {
      this.heapFactory = heapFactory;
   }

   private State initial(final MethodInfo entryPoint, final Vm<State> vm, final Object... args) {
      return initial(vm, heapFactory(), classLoader(), instructionSource(), entryPoint, args);
   }

   public Vm<State> build(final MethodInfo entryPoint, final Object... args) {
      final Vm<State> vm = build();
      vm.initial(initial(entryPoint, vm, args));
      return vm;
   }

   public static State initial(
         final Vm<State> vm,
         final HeapFactory heapFactory,
         final SClassLoader classLoader,
         final InstructionSource instructions,
         final MethodInfo info,
         final Object[] args) {
      return initial(vm, heapFactory, classLoader, instructions, new AsmSMethodName(info.klass(), info.name(), info.desc()), args);
   }

   static State initial(
         final Vm<State> vm,
         final HeapFactory heapFactory,
         final SClassLoader classLoader,
         final InstructionSource instructions,
         final SMethodDescriptor methodName,
         final Object[] args) {
      final Instruction defineClassClass = classLoader.defineBootstrapClassesInstruction();
      final Instruction initThread = InitThreadOp.initThreadInstruction(instructions);
      final Instruction loadArgs = classLoader.loadArgsInstruction(args);

      invokeStatic(methodName, new AbstractInstructionSink(){
         @Override public void nextInstruction(final Vop node) {
            defineClassClass.nextIs(initThread).nextIs(loadArgs).nextIs(new InstructionInternal(node));
         }}, instructions);

      final StaticsImpl statics = new StaticsImpl(classLoader);

      final DequeStack stack = new DequeStack();
      stack.push(new SnapshotableStackFrame(null, defineClassClass, 0, methodName.argSize()));
      return new StateImpl(vm, statics, stack, heapFactory.heap(), classLoader.initialMeta());
   }
}
