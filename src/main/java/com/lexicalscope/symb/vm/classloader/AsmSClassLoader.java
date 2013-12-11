package com.lexicalscope.symb.vm.classloader;

import static org.objectweb.asm.Type.getInternalName;

import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class AsmSClassLoader implements SClassLoader {
   private final Instructions instructions;
   private final InstructionFactory instructionFactory;
   private final ByteCodeReader byteCodeReader;

   public AsmSClassLoader(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
      this.instructions = new BaseInstructions(instructionFactory);
      this.byteCodeReader = new CachingByteCodeReader(instructions);
   }

   public AsmSClassLoader() {
      this(new ConcInstructionFactory());
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClassLoader#load(java.lang.String, com.lexicalscope.symb.vm.classloader.ClassLoaded)
    */
   @Override public SClass load(final String name, final ClassLoaded classLoaded) {
      final SClass result = byteCodeReader.load(this, name, classLoaded);
      classLoaded.loaded(result);
      return result;
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

   @Override public MethodBody resolveNative(final SMethodName methodName) {
      // TODO[tim]: make native methods do something

      if(methodName.equals(new SMethodName("java/lang/Class", "getClassLoader0", "()Ljava/lang/ClassLoader;"))) {
         return instructions.statements().maxStack(1).aconst_null().return1().build();
      } else if (methodName.equals(new SMethodName("java/lang/Class", "desiredAssertionStatus0", "(Ljava/lang/Class;)Z"))) {
         return instructions.statements().maxStack(1).iconst_0().return1().build();
      } else if (methodName.equals(new SMethodName("java/lang/Class", "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;"))) {
         // TODO[tim] we need to somewhere store the mapping between class objects and the class they represent
         return instructions.statements().maxStack(1).newObject("java/lang/Class").return1().build();
      }

      if(!methodName.isVoidMethod()) throw new UnsupportedOperationException("only void native methods are supported - " + methodName);
      return instructions.statements().returnVoid().build();
   }

   @Override public InstructionNode defineClassClassInstruction() {
      return new InstructionInternalNode(instructions.defineClass(getInternalName(Class.class)));
   }

   boolean foo() {
      return false;
   }
}
