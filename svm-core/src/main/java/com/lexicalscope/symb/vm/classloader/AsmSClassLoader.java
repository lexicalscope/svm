package com.lexicalscope.symb.vm.classloader;

import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.classloader.asm.AsmSClass;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.Instructions;
import com.lexicalscope.symb.vm.instructions.StatementBuilder;
import com.lexicalscope.symb.vm.instructions.ops.DefineClassOp;
import com.lexicalscope.symb.vm.natives.DefaultNativeMethods;
import com.lexicalscope.symb.vm.natives.NativeMethods;

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

   @Override public AsmSClass load(final String name, final ClassLoaded classLoaded) {
      return byteCodeReader.load(this, name, classLoaded);
   }

   @Override public AsmSClass load(final Class<?> klass, final ClassLoaded classLoaded) {
      return load(getInternalName(klass), classLoaded);
   }

   @Override public AsmSClass load(final Class<?> klass) {
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

   @Override public InstructionNode defineBootstrapClassesInstruction() {
      final List<String> bootstrapClasses = new ArrayList<>();
      bootstrapClasses.add(getInternalName(Class.class));
      bootstrapClasses.add(getInternalName(String.class));
      bootstrapClasses.add(getInternalName(Thread.class));
      bootstrapClasses.addAll(DefineClassOp.primitives);

      return new InstructionInternalNode(instructions.defineClass(bootstrapClasses));
   }

   @Override public InstructionNode loadArgsInstruction(final Object[] args) {
      final StatementBuilder builder = new StatementBuilder(instructions).nop();
      for (final Object object : args) {
         builder.loadArg(object);
      }
      return builder.buildInstruction();
   }

   @Override public InstructionNode initThreadInstruction() {
      final InstructionInternalNode firstInstruction = new InstructionInternalNode(instructions.initThread());
      firstInstruction.next(new InstructionInternalNode(instructions.createInvokeSpecial(new AsmSMethodName("java/lang/Thread", "<init>", "()V"))));
      return firstInstruction;
   }

   @Override public Object init(final String desc) {
      return instructions.initialFieldValue(desc);
   }
}
