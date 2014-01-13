package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldNode;

public final class SField {
   private final SFieldName fieldName;
   private final FieldNode fieldNode;
   private final Object init;

   public SField(final SFieldName fieldName, final FieldNode fieldNode, final Object init) {
      this.fieldName = fieldName;
      this.fieldNode = fieldNode;
      this.init = init;
   }

   public String desc() {
      return fieldNode.desc;
   }

   public SFieldName name() {
      return fieldName;
   }

   public boolean isStatic() {
      return (fieldNode.access & Opcodes.ACC_STATIC) != 0;
   }

   public Object init() {
      return init;
   }

   @Override public String toString() {
      return fieldName.toString();
   }
}
