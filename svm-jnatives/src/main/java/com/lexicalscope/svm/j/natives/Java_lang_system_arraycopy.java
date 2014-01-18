package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.statementBuilder.MethodBody;

public class Java_lang_system_arraycopy extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_arraycopy() {
      super("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxLocals(5).arrayCopy().returnVoid().build();
   }
}
