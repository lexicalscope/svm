package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.NanoTimeOp;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_system_nanoTime extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_nanoTime() {
      super("java/lang/System", "nanoTime", "()J");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(2).linear(new NanoTimeOp()).return2().build();
   }
}
