package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.array.ArrayCopyOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.vm.j.MethodBody;

public class Java_lang_system_arraycopy extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_arraycopy() {
      super("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxLocals(5).linearOp(new ArrayCopyOp()).returnVoid().build();
   }
}
