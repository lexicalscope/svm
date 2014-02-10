package com.lexicalscope.svm.partition.trace;

import com.lexicalscope.svm.metastate.MetaKey;

public class CrossingCallMetaKey implements MetaKey<Boolean> {
   public static final MetaKey<Boolean> CROSSINGCALL = new CrossingCallMetaKey();
   private CrossingCallMetaKey() { }

   @Override public Class<Boolean> valueType() {
      return Boolean.class;
   }
}
