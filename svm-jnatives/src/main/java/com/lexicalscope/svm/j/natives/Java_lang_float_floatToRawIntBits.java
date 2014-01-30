package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.concrete.nativ3.FloatToRawIntBits;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;

public class Java_lang_float_floatToRawIntBits extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_lang_float_floatToRawIntBits() {
      super("java/lang/Float", "floatToRawIntBits", "(F)I");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return instructions.statements()
            .maxStack(1)
            .maxLocals(1)
            .fload(0)
            .linear(new FloatToRawIntBits())
            .return1()
            .build();
   }
}
