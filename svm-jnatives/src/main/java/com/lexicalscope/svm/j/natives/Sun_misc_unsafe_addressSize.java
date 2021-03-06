package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.vm.j.MethodBody;

public final class Sun_misc_unsafe_addressSize extends AbstractNativeMethodDef {
   public Sun_misc_unsafe_addressSize() {
      super("sun/misc/Unsafe", "addressSize", "()I");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      // there is not really good answer here, because everything takes up "1" in our heap.
      // we should return either 4 or 8, but will try 1 and see what happens
      return statements(instructions).maxStack(1).iconst(1).return1(name()).build();
   }
}
