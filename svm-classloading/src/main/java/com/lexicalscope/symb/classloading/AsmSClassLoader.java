package com.lexicalscope.symb.classloading;

import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.svm.j.instruction.concrete.klass.DefineClassOp;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.InitThreadOp;
import com.lexicalscope.svm.j.instruction.factory.BaseInstructions;
import com.lexicalscope.svm.j.instruction.factory.ConcInstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.svm.j.natives.NativeMethods;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class AsmSClassLoader implements SClassLoader {
   private final Instructions instructions;
   private final InstructionFactory instructionFactory;
   private final ByteCodeReader byteCodeReader;
   private final NativeMethods natives;

   public AsmSClassLoader(final InstructionFactory instructionFactory, final NativeMethods natives) {
      this.instructionFactory = instructionFactory;
      this.natives = natives;
      this.instructions = new BaseInstructions(instructionFactory);
      this.byteCodeReader = new CachingByteCodeReader(instructions);
   }

   public AsmSClassLoader() {
      this(new ConcInstructionFactory(), DefaultNativeMethods.natives());
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

   // TODO[tim]: this method is in the !wrong! place
   @Override public Snapshotable<?> initialMeta() {
      return instructionFactory.initialMeta();
   }

   @Override public MethodBody resolveNative(final SMethodDescriptor methodName) {
      return natives.resolveNative(instructions, methodName);
   }

   @Override public Instruction defineBootstrapClassesInstruction() {
      final List<String> bootstrapClasses = new ArrayList<>();
      bootstrapClasses.add(getInternalName(Class.class));
      bootstrapClasses.add(getInternalName(String.class));
      bootstrapClasses.add(getInternalName(Thread.class));
      bootstrapClasses.addAll(DefineClassOp.primitiveClasses);
      bootstrapClasses.addAll(DefineClassOp.primitiveArrays);

      return instructions.statements().linear(instructions.defineClass(bootstrapClasses)).buildInstruction();
   }

   @Override public Instruction loadArgsInstruction(final Object[] args) {
      final StatementBuilder builder = new StatementBuilder(instructions).nop();
      for (final Object object : args) {
         builder.loadArg(object);
      }
      return builder.buildInstruction();
   }

   @Override public Instruction initThreadInstruction() {
      return new StatementBuilder(instructions)
         .linear(new InitThreadOp())
         .createInvokeSpecial(new AsmSMethodName("java/lang/Thread", "<init>", "()V"))
         .buildInstruction();
   }

   @Override public Object init(final String desc) {
      return instructions.initialFieldValue(desc);
   }
}
