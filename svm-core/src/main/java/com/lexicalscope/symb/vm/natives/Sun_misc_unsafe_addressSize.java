package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.builder.MethodBody;
import com.lexicalscope.svm.j.instruction.concrete.Instructions;

public final class Sun_misc_unsafe_addressSize extends AbstractNativeMethodDef {
   public Sun_misc_unsafe_addressSize() {
      super("sun/misc/Unsafe", "addressSize", "()I");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      // there is not really good answer here, because everything takes up "1" in our heap.
      // we should return either 4 or 8, but will try 1 and see what happens
      return instructions.statements().maxStack(1).iconst(1).return1().build();
   }
}
