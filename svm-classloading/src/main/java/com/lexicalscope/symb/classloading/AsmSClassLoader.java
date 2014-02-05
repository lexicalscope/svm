package com.lexicalscope.symb.classloading;

import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.svm.j.instruction.NoOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.DefineClassOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.DefinePrimitiveClassesOp;
import com.lexicalscope.svm.j.instruction.factory.BaseInstructionSource;
import com.lexicalscope.svm.j.instruction.factory.ConcInstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.svm.j.natives.NativeMethods;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class AsmSClassLoader implements SClassLoader {
   private final InstructionSource instructions;
   private final ByteCodeReader byteCodeReader;
   private final NativeMethods natives;

   public AsmSClassLoader(
         final InstructionSource instructionSource,
         final NativeMethods natives) {
      this.natives = natives;
      this.instructions = instructionSource;
      this.byteCodeReader = new CachingByteCodeReader(instructions);
   }

   public AsmSClassLoader() {
      this(new ConcInstructionFactory());
   }

   private AsmSClassLoader(final InstructionFactory instructionFactory) {
      this(new BaseInstructionSource(instructionFactory), DefaultNativeMethods.natives());
   }

   @Override public SClass load(final String name, final ClassLoaded classLoaded) {
      return byteCodeReader.load(this, name, classLoaded);
   }

   @Override public SClass load(final Class<?> klass, final ClassLoaded classLoaded) {
      return load(getInternalName(klass), classLoaded);
   }

   @Override public SClass load(final Class<?> klass) {
      return load(klass, new NullClassLoaded());
   }

   public Object load(final String string) {
      return load(string, new NullClassLoaded());
   }

   @Override public MethodBody resolveNative(final SMethodDescriptor methodName) {
      return natives.resolveNative(instructions, methodName);
   }

   @Override public void defineBootstrapClassesInstruction(final InstructionSink sink) {
      final List<String> bootstrapClasses = new ArrayList<>();
      bootstrapClasses.add(getInternalName(Class.class));
      bootstrapClasses.add(getInternalName(String.class));
      bootstrapClasses.add(getInternalName(Thread.class));

      sink.loadingOp(new DefinePrimitiveClassesOp(new DefineClassOp(bootstrapClasses)), new NoOp());
   }

   @Override public Instruction loadArgsInstruction(final Object[] args) {
      final StatementBuilder builder = new StatementBuilder(instructions).nop();
      for (final Object object : args) {
         builder.loadArg(object);
      }
      return builder.buildInstruction();
   }
}
