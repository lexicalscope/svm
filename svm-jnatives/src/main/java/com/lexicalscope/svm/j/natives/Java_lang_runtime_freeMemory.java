package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.vm.j.MethodBody;

public class Java_lang_runtime_freeMemory extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_runtime_freeMemory() {
      super("java/lang/Runtime", "freeMemory", "()J");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(2).lconst(4294967296L).return2(name()).build();
   }
}
