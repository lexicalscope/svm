package com.lexicalscope.symb.vm.natives;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.svm.j.instruction.concrete.Instructions;
import com.lexicalscope.svm.j.instruction.concrete.MethodBody;
import com.lexicalscope.symb.code.AsmSMethodName;
import com.lexicalscope.symb.vm.SMethodDescriptor;

public class DefaultNativeMethods implements NativeMethods {
   private final Map<SMethodDescriptor, NativeMethodDef> natives;

   public DefaultNativeMethods(final Map<SMethodDescriptor, NativeMethodDef> natives) {
      this.natives = natives;
   }

   @Override public MethodBody resolveNative(final Instructions instructions, final SMethodDescriptor methodName) {
      final NativeMethodDef methodDef = natives.get(methodName);
      if(methodDef != null) {
         return methodDef.instructions(instructions);
      }
      if (methodName.equals(new AsmSMethodName("java/lang/Class", "desiredAssertionStatus0", "(Ljava/lang/Class;)Z"))) {
         return instructions.statements().maxStack(1).iconst_0().return1().build();
      } else if (methodName.equals(new AsmSMethodName("java/lang/Class", "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;"))) {
         // TODO[tim] we need to somewhere store the mapping between class
         // objects and the class they represent
         return instructions.statements().maxLocals(1).maxStack(1).getPrimitiveClass().return1().build();
      }

      if (!methodName.isVoidMethod()) { throw new UnsupportedOperationException("only void native methods are supported - " + methodName); }
      return instructions.statements().returnVoid().build();
   }
   public static NativeMethods natives() {
      return natives(Arrays.<NativeMethodDef>asList(
            new Java_lang_class_getClassLoader0(),
            new Java_lang_system_identityHashCode(),
            new Java_lang_system_nanoTime(),
            new Java_lang_system_currentTimeMillis(),
            new Java_lang_thread_currentThread(),
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
      final Map<SMethodDescriptor, NativeMethodDef> nativesMap = new HashMap<>();
      for (final NativeMethodDef nativeMethodDef : natives) {
         nativesMap.put(nativeMethodDef.name(), nativeMethodDef);
      }
      return new DefaultNativeMethods(nativesMap);
   }
}
