package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.concrete.Instructions;
import com.lexicalscope.svm.j.instruction.concrete.MethodBody;

public class Java_lang_object_hashCode extends AbstractNativeMethodDef {
   public Java_lang_object_hashCode() {
      super("java/lang/Object", "hashCode", "()I");
   }
   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).aload(0).addressToHashCode().return1().build();
   }
}
