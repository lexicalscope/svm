package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.statementBuilder.MethodBody;

public final class Java_lang_class_getClassLoader0 extends AbstractNativeMethodDef {
   public Java_lang_class_getClassLoader0() {
      super("java/lang/Class", "getClassLoader0", "()Ljava/lang/ClassLoader;");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).aconst_null().return1().build();
   }
}
