package com.lexicalscope.svm.vm.conc;

import static com.lexicalscope.svm.classloading.JarClassRepository.loadFromLibDirectoryInSameJarFileAs;
import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;
import static com.lexicalscope.svm.stack.MethodScope.STATIC;
import static com.lexicalscope.svm.vm.j.InstructionCode.synthetic;
import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.classloading.AsmSClassLoader;
import com.lexicalscope.svm.classloading.ClassSource;
import com.lexicalscope.svm.classloading.ClasspathClassRepository;
import com.lexicalscope.svm.classloading.SClassLoader;
import com.lexicalscope.svm.classloading.StaticsImpl;
import com.lexicalscope.svm.heap.HeapFactory;
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
import com.lexicalscope.svm.j.instruction.instrumentation.InstrumentationBuilder;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentor;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.svm.j.natives.NativeMethods;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.svm.metastate.HashMetaState;
import com.lexicalscope.svm.metastate.MetaKey;
import com.lexicalscope.svm.metastate.MetaState;
import com.lexicalscope.svm.stack.DequeStack;
import com.lexicalscope.svm.stack.SnapshotableStackFrame;
import com.lexicalscope.svm.vm.Vm;
import com.lexicalscope.svm.vm.VmImpl;
import com.lexicalscope.svm.vm.conc.checkingheap.CheckingHeapFactory;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.JavaConstants;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.j.StateImpl;
import com.lexicalscope.svm.vm.j.code.AsmSMethodName;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public final class JvmBuilder {
   private InstructionFactory instructionFactory = new ConcInstructionFactory();
   private InstructionSourceFactory instructionSourceFactory = new BaseInstructionSourceFactory();
   private HeapFactory heapFactory = new CheckingHeapFactory();
   private final NativeMethods natives = DefaultNativeMethods.natives();
   private final InstrumentationBuilder instrumentationBuilder = new InstrumentationBuilder();
   private final MetaState metaState = new HashMetaState();
   private ClassSource classSource = new ClasspathClassRepository();

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

   public JvmBuilder instrument(final Matcher<? super SMethodDescriptor> methodMatcher, final Instrumentor instrumentation) {
      instrumentationBuilder.instrument(methodMatcher, instrumentation);
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

   public JvmBuilder loadFrom(final Class<?> loadFromWhereverThisWasLoaded) {
      this.classSource = loadFromLibDirectoryInSameJarFileAs(loadFromWhereverThisWasLoaded);
      return this;
   }

   public Vm<State> build(final MethodInfo entryPoint, final Object... args) {
      return build(new AsmSMethodName(entryPoint.klass(), entryPoint.name(), entryPoint.desc()), args);
   }

   public Vm<State> build(final SMethodDescriptor entryPointName, final Object... args) {
      final Vm<State> vm = build();

      final InstructionSource instructions = instructionSourceFactory.instructionSource(instructionFactory);
      System.out.println("using classSource " + classSource);
      final SClassLoader classLoader = new AsmSClassLoader(
            instructions,
            instrumentationBuilder.instrumentation(instructions),
            natives(),
            classSource);

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

   public Vm<State> build() {
      return new VmImpl<State>();
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