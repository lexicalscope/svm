package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.object.AddressToHashCodeOp;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_system_identityHashCode extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_system_identityHashCode() {
      super("java/lang/System", "identityHashCode", "(Ljava/lang/Object;)I");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(1).maxLocals(1).aload(0).linearOp(new AddressToHashCodeOp()).return1().build();
   }
}
