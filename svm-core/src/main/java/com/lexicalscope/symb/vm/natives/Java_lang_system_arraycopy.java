package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class Java_lang_system_arraycopy extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_arraycopy() {
      super("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxLocals(5).arrayCopy().returnVoid().build();
   }
}
