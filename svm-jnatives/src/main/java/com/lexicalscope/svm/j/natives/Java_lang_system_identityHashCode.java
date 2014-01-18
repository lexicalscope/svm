package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.statementBuilder.MethodBody;

public class Java_lang_system_identityHashCode extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_identityHashCode() {
      super("java/lang/System", "identityHashCode", "(Ljava/lang/Object;)I");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).aload(0).addressToHashCode().return1().build();
   }
}
