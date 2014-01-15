package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class Java_lang_system_nanoTime extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_nanoTime() {
      super("java/lang/System", "nanoTime", "()J");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(2).nanoTime().return2().build();
   }
}
