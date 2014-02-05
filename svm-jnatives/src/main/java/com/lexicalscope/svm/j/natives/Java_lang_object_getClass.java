package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.object.GetClassOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_object_getClass extends AbstractNativeMethodDef {
   public Java_lang_object_getClass() {
      super("java/lang/Object", "getClass", "()Ljava/lang/Class;");
   }
   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions)
               .maxStack(1)
               .maxLocals(1)
               .aload(0)
               .linearOp(new GetClassOp())
               .return1().build();
   }
}
