package com.lexicalscope.symb.vm.classloader;

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

   public String name() {
      return fieldNode.name;
   }
}
