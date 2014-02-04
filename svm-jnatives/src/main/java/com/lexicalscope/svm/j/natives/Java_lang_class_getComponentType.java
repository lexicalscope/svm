package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.object.GetComponentClassOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_class_getComponentType extends AbstractNativeMethodDef {
   public Java_lang_class_getComponentType() {
      super("java/lang/Class", "getComponentType", "()Ljava/lang/Class;");
   }
   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions)
               .maxStack(1)
               .maxLocals(1)
               .aload(0)
               .linear(new GetComponentClassOp())
               .return1().build();
   }
}
