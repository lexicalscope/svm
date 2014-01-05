package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.instructions.Instructions;
import com.lexicalscope.symb.vm.instructions.ops.NewArrayOp;

public final class Sun_misc_unsafe_arrayBaseOffset extends AbstractNativeMethodDef implements NativeMethodDef {
   public Sun_misc_unsafe_arrayBaseOffset() {
      super("sun/misc/Unsafe", "arrayBaseOffset", "(Ljava/lang/Class;)I");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).iconst(NewArrayOp.ARRAY_PREAMBLE).return1().build();
   }
}
