package com.lexicalscope.symb.vm.classloader;

import static org.objectweb.asm.Type.getInternalName;

import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.Instructions;
import com.lexicalscope.symb.vm.instructions.ops.NewArrayOp;

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

   @Override public MethodBody resolveNative(final SMethodName methodName) {
      // TODO[tim]: make native methods do something

      if (methodName.equals(new SMethodName("java/lang/Class", "getClassLoader0", "()Ljava/lang/ClassLoader;"))) {
         return instructions.statements().maxStack(1).aconst_null().return1().build();
      } else if (methodName.equals(new SMethodName("java/lang/Class", "desiredAssertionStatus0", "(Ljava/lang/Class;)Z"))) {
         return instructions.statements().maxStack(1).iconst_0().return1().build();
      } else if (methodName.equals(new SMethodName("java/lang/Class", "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;"))) {
         // TODO[tim] we need to somewhere store the mapping between class
         // objects and the class they represent
         return instructions.statements().maxStack(1).newObject("java/lang/Class").return1().build();
      } else if (methodName.equals(new SMethodName("java/lang/System", "identityHashCode", "(Ljava/lang/Object;)I"))) {
         return instructions.statements().maxStack(1).maxLocals(1).aload(0).addressToHashCode().return1().build();
      } else if (methodName.equals(new SMethodName("java/lang/System", "nanoTime", "()J"))) {
         return instructions.statements().maxStack(2).nanoTime().return2().build();
      } else if (methodName.equals(new SMethodName("java/lang/System", "currentTimeMillis", "()J"))) {
         return instructions.statements().maxStack(2).currentTimeMillis().return2().build();
      } else if (methodName.equals(new SMethodName("java/lang/Thread", "currentThread", "()Ljava/lang/Thread;"))) {
         // TODO[tim] we need to somehow store a thread object, probably
         // initalise it at the start
         return instructions.statements().maxStack(1).currentThread().return1().build();
      } else if (methodName.equals(new SMethodName("java/lang/Runtime", "freeMemory", "()J"))) {
         return instructions.statements().maxStack(2).lconst(4294967296L).return2().build();
      } else if (methodName.equals(new SMethodName("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V"))) {
         return instructions.statements().maxLocals(5).arrayCopy().returnVoid().build();
      } else if (methodName.equals(new SMethodName("java/lang/Float", "floatToRawIntBits", "(F)I"))) {
         return instructions.statements().maxStack(1).maxLocals(1).fload(0).floatToRawIntBits().return1().build();
      } else if (methodName.equals(new SMethodName("java/lang/Double", "doubleToRawLongBits", "(D)J"))) {
         return instructions.statements().maxStack(2).maxLocals(1).dload(0).doubleToRawLongBits().return2().build();
      } else if (methodName.equals(new SMethodName("java/lang/Object", "hashCode", "()I"))) {
         return instructions.statements().maxStack(1).maxLocals(1).aload(0).addressToHashCode().return1().build();
      } else if (methodName.equals(new SMethodName("sun/misc/Unsafe", "arrayBaseOffset", "(Ljava/lang/Class;)I"))) {
         return instructions.statements().maxStack(1).maxLocals(1).iconst(NewArrayOp.ARRAY_PREAMBLE).return1().build();
      } else if (methodName.equals(new SMethodName("sun/misc/Unsafe", "arrayIndexScale", "(Ljava/lang/Class;)I"))) {
         return instructions.statements().maxStack(1).maxLocals(1).iconst(1).return1().build();
      } else if (methodName.equals(new SMethodName("sun/misc/Unsafe", "addressSize", "()I"))) {
         // there is not really good answer here, because everything takes up "1" in our heap.
         // we should return either 4 or 8, but will try 1 and see what happens
         return instructions.statements().maxStack(1).iconst(1).return1().build();
      } else if (methodName.equals(new SMethodName("sun/reflect/Reflection", "getCallerClass", "()Ljava/lang/Class;"))) {
         return instructions.statements().maxStack(1).maxLocals(1).getCallerClass().return1().build();
      } else if (methodName.equals(new SMethodName("java/security/AccessController", "doPrivileged", "(Ljava/security/PrivilegedAction;)Ljava/lang/Object;"))) {
         return instructions.statements().maxStack(1).maxLocals(1).aload(0).invokeInterface("java/security/PrivilegedAction", "run", "()Ljava/lang/Object;").return1().build();
      }

      if (!methodName.isVoidMethod()) { throw new UnsupportedOperationException("only void native methods are supported - " + methodName); }
      return instructions.statements().returnVoid().build();
   }

   @Override public InstructionNode defineClassClassInstruction() {
      return new InstructionInternalNode(instructions.defineClass(getInternalName(Class.class)));
   }

   @Override public InstructionNode definePrimitiveClassesInstruction() {
      final InstructionInternalNode first = definePrimitiveClassInstruction(boolean[].class);
      first.next(definePrimitiveClassInstruction(byte[].class))
      .next(definePrimitiveClassInstruction(char[].class))
      .next(definePrimitiveClassInstruction(short[].class))
      .next(definePrimitiveClassInstruction(int[].class))
      .next(definePrimitiveClassInstruction(float[].class))
      .next(definePrimitiveClassInstruction(long[].class))
      .next(definePrimitiveClassInstruction(double[].class))
      .next(definePrimitiveClassInstruction(Object[].class));
      return first;
   }

   private InstructionInternalNode definePrimitiveClassInstruction(final Class<?> klass) {
      return new InstructionInternalNode(instructions.definePrimitiveClass(getInternalName(klass)));
   }

   @Override public InstructionNode defineStringClassInstruction() {
      return new InstructionInternalNode(instructions.defineClass(getInternalName(String.class)));
   }

   @Override public InstructionNode defineThreadClassInstruction() {
      return new InstructionInternalNode(instructions.defineClass(getInternalName(Thread.class)));
   }

   @Override public InstructionNode initThreadInstruction() {
      final InstructionInternalNode firstInstruction = new InstructionInternalNode(instructions.initThread());
      firstInstruction.next(new InstructionInternalNode(instructions.createInvokeSpecial(new SMethodName("java/lang/Thread", "<init>", "()V"))));
      return firstInstruction;
   }

   @Override public Object init(final String desc) {
      return instructions.initialFieldValue(desc);
   }
}
