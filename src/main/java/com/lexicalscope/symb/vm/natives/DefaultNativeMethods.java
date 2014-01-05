package com.lexicalscope.symb.vm.natives;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.instructions.Instructions;
import com.lexicalscope.symb.vm.instructions.ops.NewArrayOp;

public class DefaultNativeMethods implements NativeMethods {
   private final Map<SMethodName, NativeMethodDef> natives;

   public DefaultNativeMethods(final Map<SMethodName, NativeMethodDef> natives) {
      this.natives = natives;
   }

   @Override public MethodBody resolveNative(final Instructions instructions, final SMethodName methodName) {
      final NativeMethodDef methodDef = natives.get(methodName);
      if(methodDef != null) {
         return methodDef.instructions(instructions);
      }
      if (methodName.equals(new SMethodName("java/lang/Class", "desiredAssertionStatus0", "(Ljava/lang/Class;)Z"))) {
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
      }

      if (!methodName.isVoidMethod()) { throw new UnsupportedOperationException("only void native methods are supported - " + methodName); }
      return instructions.statements().returnVoid().build();
   }

   public static NativeMethods natives() {
      return natives(Arrays.<NativeMethodDef>asList(
            new Java_lang_class_getClassLoader0(),
            new Java_security_accessController_doPrivileged(),
            new Sun_reflect_reflection_getCallerClass()
            ));
   }


   public static NativeMethods natives(final List<NativeMethodDef> natives) {
      final Map<SMethodName, NativeMethodDef> nativesMap = new HashMap<>();
      for (final NativeMethodDef nativeMethodDef : natives) {
         nativesMap.put(nativeMethodDef.name(), nativeMethodDef);
      }
      return new DefaultNativeMethods(nativesMap);
   }
}
