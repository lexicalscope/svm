package com.lexicalscope.svm.j.instruction.concrete.object;

import com.lexicalscope.svm.metastate.MetaKey;

public final class PartitionTagMetaKey implements MetaKey<Object> {
   public static final PartitionTagMetaKey PARTITION_TAG = new PartitionTagMetaKey();

   private PartitionTagMetaKey() {}

   @Override public Class<Object> valueType() {
      return Object.class;
   }

   @Override public String toString() {
      return "OBJECT_TAG";
   }
}
