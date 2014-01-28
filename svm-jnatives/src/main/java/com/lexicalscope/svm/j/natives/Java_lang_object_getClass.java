package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.concrete.object.GetClassOp;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_object_getClass extends AbstractNativeMethodDef {
   public Java_lang_object_getClass() {
      super("java/lang/Object", "getClass", "()Ljava/lang/Class;");
   }
   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions
               .statements()
               .maxStack(1)
               .maxLocals(1)
               .aload(0)
               .linear(new GetClassOp())
               .return1().build();
   }
}
