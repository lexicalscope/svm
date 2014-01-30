package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.concrete.object.AddressToHashCodeOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_object_hashCode extends AbstractNativeMethodDef {
   public Java_lang_object_hashCode() {
      super("java/lang/Object", "hashCode", "()I");
   }
   @Override public MethodBody instructions(final InstructionSource instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).aload(0).linear(new AddressToHashCodeOp()).return1().build();
   }
}
