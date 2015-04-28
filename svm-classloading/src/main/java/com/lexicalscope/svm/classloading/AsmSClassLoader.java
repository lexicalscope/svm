package com.lexicalscope.svm.classloading;

import static org.objectweb.asm.Type.getInternalName;

import com.lexicalscope.svm.j.instruction.factory.BaseInstructionSource;
import com.lexicalscope.svm.j.instruction.factory.ConcInstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentation;
import com.lexicalscope.svm.j.instruction.instrumentation.NullInstrumentation;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.svm.j.natives.NativeMethods;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.MethodBody;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class AsmSClassLoader implements SClassLoader {
   private final InstructionSource instructions;
   private final ByteCodeReader byteCodeReader;
   private final NativeMethods natives;
   private final Instrumentation instrumentation;

   public AsmSClassLoader(
         final InstructionSource instructionSource,
         final Instrumentation instrumentation,
         final NativeMethods natives,
         final ClassSource classSource) {
      this.instrumentation = instrumentation;
      this.natives = natives;
      this.instructions = instructionSource;
      this.byteCodeReader = new CachingByteCodeReader(instructions, classSource);
   }

   public AsmSClassLoader() {
      this(new ConcInstructionFactory());
   }

   private AsmSClassLoader(final InstructionFactory instructionFactory) {
      this(new BaseInstructionSource(instructionFactory), new NullInstrumentation(), DefaultNativeMethods.natives(), new ClasspathClassRepository());
   }

   @Override public SClass load(final String name) {
      return byteCodeReader.load(this, name);
   }

   @Override public SClass load(final Class<?> klass) {
      return load(getInternalName(klass));
   }

   @Override public MethodBody resolveNative(final SMethodDescriptor methodName) {
      return natives.resolveNative(instructions, methodName);
   }

   @Override public Instruction instrument(final SMethodDescriptor name, final Instruction methodEntry) {
      return instrumentation.instrument(name, methodEntry);
   }
}
