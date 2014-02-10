package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.vm.j.MethodBody;

public final class Java_lang_reflect_array_newArray extends AbstractNativeMethodDef {
   public Java_lang_reflect_array_newArray() {
      super("java/lang/reflect/Array", "newArray", "(Ljava/lang/Class;I)Ljava/lang/Object;");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxLocals(2).maxStack(2).aload(1).reflectionnewarray().return1().build();
   }
}
