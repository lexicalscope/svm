package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.GetCallerClass;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.j.MethodBody;

public final class Sun_reflect_reflection_getCallerClass extends AbstractNativeMethodDef {
   public Sun_reflect_reflection_getCallerClass() {
      super("sun/reflect/Reflection", "getCallerClass", "()Ljava/lang/Class;");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).linear(new GetCallerClass()).return1().build();
   }
}
