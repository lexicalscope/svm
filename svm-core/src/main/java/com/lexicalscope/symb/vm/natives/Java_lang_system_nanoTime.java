package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.statementBuilder.MethodBody;

public class Java_lang_system_nanoTime extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_nanoTime() {
      super("java/lang/System", "nanoTime", "()J");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(2).nanoTime().return2().build();
   }
}
