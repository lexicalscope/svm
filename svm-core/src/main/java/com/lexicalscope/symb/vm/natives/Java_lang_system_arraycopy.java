package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.builder.MethodBody;
import com.lexicalscope.svm.j.instruction.concrete.Instructions;

public class Java_lang_system_arraycopy extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_arraycopy() {
      super("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxLocals(5).arrayCopy().returnVoid().build();
   }
}
