package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.concrete.Instructions;
import com.lexicalscope.svm.j.instruction.concrete.MethodBody;

public final class Sun_reflect_reflection_getCallerClass extends AbstractNativeMethodDef {
   public Sun_reflect_reflection_getCallerClass() {
      super("sun/reflect/Reflection", "getCallerClass", "()Ljava/lang/Class;");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).getCallerClass().return1().build();
   }
}
