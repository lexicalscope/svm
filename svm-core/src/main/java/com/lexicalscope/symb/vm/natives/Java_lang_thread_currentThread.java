package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class Java_lang_thread_currentThread extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_thread_currentThread() {
      super("java/lang/Thread", "currentThread", "()Ljava/lang/Thread;");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).currentThread().return1().build();
   }
}
