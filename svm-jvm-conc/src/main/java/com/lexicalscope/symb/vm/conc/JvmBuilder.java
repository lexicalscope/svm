package com.lexicalscope.symb.vm.conc;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.InitThreadOp;
import com.lexicalscope.svm.j.instruction.factory.AbstractInstructionSink;
import com.lexicalscope.svm.j.instruction.factory.BaseInstructionSource;
import com.lexicalscope.svm.j.instruction.factory.ConcInstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
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

   public JvmBuilder instructionFactory(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
      return this;
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

   public JvmBuilder heapFactory(final HeapFactory heapFactory) {
      this.heapFactory = heapFactory;
      return this;
   }

   public Vm<State> build(final MethodInfo entryPoint, final Object... args) {
      return build(new AsmSMethodName(entryPoint.klass(), entryPoint.name(), entryPoint.desc()), args);
   }

   public Vm<State> build(final SMethodDescriptor entryPointName, final Object... args) {
      final Vm<State> vm = new VmImpl<State>();
      final SClassLoader classLoader = new AsmSClassLoader(instructionFactory, natives());
      final BaseInstructionSource instructionSource = instructionSource();

//      final Instruction initialInstruction = instructionSource.statements()
//         .instruction(classLoader.defineBootstrapClassesInstruction())
//         .instruction(InitThreadOp.initThreadInstruction(instructionSource))
//         .instruction(classLoader.loadArgsInstruction(args))
//         .createInvokeStatic(entryPointName).buildInstruction();

      final Instruction defineClassClass = classLoader.defineBootstrapClassesInstruction();
      final Instruction initThread = InitThreadOp.initThreadInstruction(instructionSource());
      final Instruction loadArgs = classLoader.loadArgsInstruction(args);

      MethodCallInstruction.invokeStatic(entryPointName, new AbstractInstructionSink(){
         @Override public void nextInstruction(final Vop node) {
            defineClassClass.nextIs(initThread).nextIs(loadArgs).nextIs(new InstructionInternal(node));
         }}, instructionSource());

      final Instruction initialInstruction = defineClassClass;

      final DequeStack stack = new DequeStack();
      stack.push(new SnapshotableStackFrame(null, initialInstruction, 0, entryPointName.argSize()));
      final State state = new StateImpl(vm, new StaticsImpl(classLoader), stack, heapFactory().heap(), classLoader.initialMeta());
      vm.initial(state);
      return vm;
   }
}