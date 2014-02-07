package com.lexicalscope.symb.partition.trace;

import com.lexicalscope.symb.metastate.MetaKey;

public class CrossingCallMetaKey implements MetaKey<Boolean> {
   public static final MetaKey<Boolean> CROSSINGCALL = new CrossingCallMetaKey();
   private CrossingCallMetaKey() { }

   @Override public Class<Boolean> valueType() {
      return Boolean.class;
   }
}
