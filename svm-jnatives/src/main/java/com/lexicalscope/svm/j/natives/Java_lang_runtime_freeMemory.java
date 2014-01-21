package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.MethodBody;

public class Java_lang_runtime_freeMemory extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_runtime_freeMemory() {
      super("java/lang/Runtime", "freeMemory", "()J");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(2).lconst(4294967296L).return2().build();
   }
}
