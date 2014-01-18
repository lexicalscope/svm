package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.concrete.array.NewArrayOp;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.statementBuilder.MethodBody;

public final class Sun_misc_unsafe_arrayBaseOffset extends AbstractNativeMethodDef implements NativeMethodDef {
   public Sun_misc_unsafe_arrayBaseOffset() {
      super("sun/misc/Unsafe", "arrayBaseOffset", "(Ljava/lang/Class;)I");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).iconst(NewArrayOp.ARRAY_PREAMBLE).return1().build();
   }
}
