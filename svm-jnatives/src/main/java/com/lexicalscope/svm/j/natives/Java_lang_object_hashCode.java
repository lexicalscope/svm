package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.object.AddressToHashCodeOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.vm.j.MethodBody;

public class Java_lang_object_hashCode extends AbstractNativeMethodDef {
   public Java_lang_object_hashCode() {
      super("java/lang/Object", "hashCode", "()I");
   }
   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(1).maxLocals(1).aload(0).linearOp(new AddressToHashCodeOp()).return1().build();
   }
}
