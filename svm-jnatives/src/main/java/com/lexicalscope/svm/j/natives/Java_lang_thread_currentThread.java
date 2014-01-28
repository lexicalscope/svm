package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.CurrentThreadOp;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_thread_currentThread extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_thread_currentThread() {
      super("java/lang/Thread", "currentThread", "()Ljava/lang/Thread;");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).linear(new CurrentThreadOp()).return1().build();
   }
}
