package com.lexicalscope.svm.j.instruction.concrete.object;

import com.lexicalscope.svm.metastate.MetaKey;

public final class ObjectTagMetaKey implements MetaKey<Object> {
   public static final ObjectTagMetaKey OBJECT_TAG = new ObjectTagMetaKey();

   private ObjectTagMetaKey() {}

   @Override public Class<Object> valueType() {
      return Object.class;
   }

   @Override public String toString() {
      return "OBJECT_TAG";
   }
}
