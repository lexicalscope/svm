package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.builder.MethodBody;
import com.lexicalscope.svm.j.instruction.concrete.Instructions;

public final class Sun_misc_unsafe_arrayIndexScale extends AbstractNativeMethodDef implements NativeMethodDef {
   public Sun_misc_unsafe_arrayIndexScale() {
      super("sun/misc/Unsafe", "arrayIndexScale", "(Ljava/lang/Class;)I");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).iconst(1).return1().build();
   }
}
