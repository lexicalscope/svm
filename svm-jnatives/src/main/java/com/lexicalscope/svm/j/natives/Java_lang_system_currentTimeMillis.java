package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.CurrentTimeMillisOp;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_system_currentTimeMillis extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_currentTimeMillis() {
      super("java/lang/System", "currentTimeMillis", "()J");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(2).linear(new CurrentTimeMillisOp()).return2().build();
   }
}
