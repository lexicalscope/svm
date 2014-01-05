package com.lexicalscope.symb.vm.natives;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.instructions.Instructions;

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
      }

      if (!methodName.isVoidMethod()) { throw new UnsupportedOperationException("only void native methods are supported - " + methodName); }
      return instructions.statements().returnVoid().build();
   }
   public static NativeMethods natives() {

      return natives(Arrays.<NativeMethodDef>asList(
            new Java_lang_class_getClassLoader0(),
            new Java_lang_runtime_freeMemory(),
            new Java_lang_system_arraycopy(),
            new Java_lang_float_floatToRawIntBits(),
            new Java_lang_double_doubleToRawLongBits(),
            new Java_lang_object_hashCode(),
            new Sun_misc_unsafe_arrayBaseOffset(),
            new Sun_misc_unsafe_arrayIndexScale(),
            new Sun_misc_unsafe_addressSize(),
            new Sun_reflect_reflection_getCallerClass(),
            new Java_security_accessController_doPrivileged()
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
