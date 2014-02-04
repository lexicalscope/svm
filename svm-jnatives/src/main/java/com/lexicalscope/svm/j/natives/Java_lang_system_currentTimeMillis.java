package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.CurrentTimeMillisOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_system_currentTimeMillis extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_currentTimeMillis() {
      super("java/lang/System", "currentTimeMillis", "()J");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(2).linear(new CurrentTimeMillisOp()).return2().build();
   }
}
