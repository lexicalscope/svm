package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_double_doubleToRawLongBits extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_double_doubleToRawLongBits() {
      super("java/lang/Double", "doubleToRawLongBits", "(D)J");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(2).maxLocals(1).dload(0).doubleToRawLongBits().return2().build();
   }
}
