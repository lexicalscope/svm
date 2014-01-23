package com.lexicalscope.symb.classloading.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldNode;

import com.lexicalscope.symb.klass.FieldDesc;

public final class AsmFieldDesc implements FieldDesc {
   private final FieldNode fieldNode;

   public AsmFieldDesc(final FieldNode fieldNode) {
      this.fieldNode = fieldNode;
   }

   @Override public String desc() {
      return fieldNode.desc;
   }

   @Override public boolean isStatic() {
      return (fieldNode.access & Opcodes.ACC_STATIC) != 0;
   }
}
