package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.builder.MethodBody;
import com.lexicalscope.svm.j.instruction.concrete.Instructions;

public final class Java_lang_class_getClassLoader0 extends AbstractNativeMethodDef {
   public Java_lang_class_getClassLoader0() {
      super("java/lang/Class", "getClassLoader0", "()Ljava/lang/ClassLoader;");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).aconst_null().return1().build();
   }
}
