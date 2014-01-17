package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.FieldConversionFactory;

public final class Ops {
   public static Vop putField(final FieldInsnNode fieldInsnNode, final FieldConversionFactory fieldConversionFactory) {
      return new PutFieldOp(fieldConversionFactory, fieldInsnNode);
   }
}
