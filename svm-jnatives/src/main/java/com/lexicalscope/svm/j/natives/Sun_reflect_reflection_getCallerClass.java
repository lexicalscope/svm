package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.GetCallerClass;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public final class Sun_reflect_reflection_getCallerClass extends AbstractNativeMethodDef {
   public Sun_reflect_reflection_getCallerClass() {
      super("sun/reflect/Reflection", "getCallerClass", "()Ljava/lang/Class;");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(1).maxLocals(1).linearOp(new GetCallerClass()).return1().build();
   }
}
