package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldNode;

public final class SField {
   private final SFieldName fieldName;
   private final FieldNode fieldNode;

   public SField(final SFieldName fieldName, final FieldNode fieldNode) {
      this.fieldName = fieldName;
      this.fieldNode = fieldNode;
   }

   public String desc() {
      return fieldNode.desc;
   }

   public String nameString() {
      // TODO[tim]: get rid of raw string uses of name
      return fieldNode.name;
   }

   public SFieldName name() {
      return fieldName;
   }

   public boolean isStatic() {
      return (fieldNode.access & Opcodes.ACC_STATIC) != 0;
   }
}
