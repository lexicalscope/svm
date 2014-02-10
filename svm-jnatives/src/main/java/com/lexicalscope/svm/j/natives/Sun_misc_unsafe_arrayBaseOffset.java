package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.array.NewArrayOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.vm.j.MethodBody;

public final class Sun_misc_unsafe_arrayBaseOffset extends AbstractNativeMethodDef implements NativeMethodDef {
   public Sun_misc_unsafe_arrayBaseOffset() {
      super("sun/misc/Unsafe", "arrayBaseOffset", "(Ljava/lang/Class;)I");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(1).maxLocals(1).iconst(NewArrayOp.ARRAY_PREAMBLE).return1().build();
   }
}
