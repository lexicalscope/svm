package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.NanoTimeOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_system_nanoTime extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_nanoTime() {
      super("java/lang/System", "nanoTime", "()J");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(2).linearOp(new NanoTimeOp()).return2().build();
   }
}
