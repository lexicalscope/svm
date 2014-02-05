package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.svm.j.instruction.instrumentation.InstrumentationBuilder.instrumentation;
import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;
import static com.lexicalscope.symb.stack.MethodScope.STATIC;
import static com.lexicalscope.symb.vm.j.InstructionCode.synthetic;
import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.svm.j.instruction.NoOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.DefineClassOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.DefinePrimitiveClassesOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.InitThreadOp;
import com.lexicalscope.svm.j.instruction.factory.BaseInstructionSourceFactory;
import com.lexicalscope.svm.j.instruction.factory.ConcInstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.svm.j.instruction.factory.InstructionSourceFactory;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentation;
import com.lexicalscope.svm.j.instruction.instrumentation.InstrumentationBuilder;
import com.lexicalscope.svm.j.instruction.instrumentation.InstrumentingInstructionSourceFactory;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.svm.j.natives.NativeMethods;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
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
import com.lexicalscope.symb.vm.j.InstructionCode;
import com.lexicalscope.symb.vm.j.JavaConstants;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.StateImpl;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;
import com.lexicalscope.symb.vm.j.metastate.HashMetaState;
import com.lexicalscope.symb.vm.j.metastate.MetaKey;
import com.lexicalscope.symb.vm.j.metastate.MetaState;

public final class JvmBuilder {
   private InstructionFactory instructionFactory = new ConcInstructionFactory();
   private InstructionSourceFactory instructionSourceFactory = new BaseInstructionSourceFactory();
   private HeapFactory heapFactory = new CheckingHeapFactory();
   private final NativeMethods natives = DefaultNativeMethods.natives();
   private InstrumentationBuilder instrumentationBuilder = new InstrumentationBuilder();
   private final MetaState metaState = new HashMetaState();

   public JvmBuilder() {
      if(getClass().desiredAssertionStatus()) {
         heapFactory = new CheckingHeapFactory();
      } else {
         heapFactory = new FastHeapFactory();
      }
   }

   public static JvmBuilder jvm() { return new JvmBuilder(); }

   public JvmBuilder instructionFactory(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
      return this;
   }

   public JvmBuilder instructionSourceFactory(final InstructionSourceFactory instructionSourceFactory) {
      this.instructionSourceFactory = instructionSourceFactory;
      return this;
   }

   public JvmBuilder instrument(final InstructionCode instruction, final Instrumentation instrumentation) {
      if(instrumentationBuilder == null) {
         instrumentationBuilder = instrumentation();
      }
      instrumentationBuilder.instrument(instruction, instrumentation);
      return this;
   }

   public NativeMethods natives() {
      return natives;
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

      final InstructionSource instructions =
            (instrumentationBuilder == null
                  ? instructionSourceFactory
                  : new InstrumentingInstructionSourceFactory(instructionSourceFactory, instrumentationBuilder.map()))
            .instructionSource(instructionFactory);

      final SClassLoader classLoader = new AsmSClassLoader(instructions, instrumentationBuilder.instrumentation2(), natives());

      final StatementBuilder statements = statements(instructions);
      defineBootstrapClassesInstruction(statements.sink(), instructions);
      InitThreadOp.initThreadInstruction(statements);
      loadArgsInstruction(statements, args);

      final Instruction initialInstruction = statements.createInvokeStatic(entryPointName).buildInstruction();

      final DequeStack stack = new DequeStack();
      stack.push(new SnapshotableStackFrame(JavaConstants.INITIAL_FRAME_NAME, STATIC, initialInstruction, 0, entryPointName.argSize()));
      vm.initial(new StateImpl(vm, new StaticsImpl(classLoader), stack, heapFactory().heap(), metaState));
      return vm;
   }

   private void loadArgsInstruction(final StatementBuilder statements, final Object[] args) {
      for (final Object object : args) {
         statements.loadArg(object);
      }
   }

   private void defineBootstrapClassesInstruction(final InstructionSink sink, final InstructionSource instructions) {
      final List<String> bootstrapClasses = new ArrayList<>();
      bootstrapClasses.add(getInternalName(Class.class));
      bootstrapClasses.add(getInternalName(String.class));
      bootstrapClasses.add(getInternalName(Thread.class));
      sink.nextOp(new LoadingInstruction(new DefinePrimitiveClassesOp(new DefineClassOp(bootstrapClasses)), new NoOp(), instructions), synthetic);
   }

   public <T> JvmBuilder meta(final MetaKey<T> key, final T initialMeta) {
      metaState.set(key, initialMeta);
      return this;
   }
}