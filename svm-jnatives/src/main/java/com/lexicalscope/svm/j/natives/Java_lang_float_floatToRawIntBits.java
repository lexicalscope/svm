package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.FloatToRawIntBits;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.vm.j.MethodBody;

public class Java_lang_float_floatToRawIntBits extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_float_floatToRawIntBits() {
      super("java/lang/Float", "floatToRawIntBits", "(F)I");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions)
            .maxStack(1)
            .maxLocals(1)
            .fload(0)
            .linearOp(new FloatToRawIntBits())
            .return1()
            .build();
   }
}
