package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public final class Sun_misc_unsafe_arrayIndexScale extends AbstractNativeMethodDef implements NativeMethodDef {
   public Sun_misc_unsafe_arrayIndexScale() {
      super("sun/misc/Unsafe", "arrayIndexScale", "(Ljava/lang/Class;)I");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(1).maxLocals(1).iconst(1).return1().build();
   }
}
