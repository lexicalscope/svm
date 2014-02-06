package com.lexicalscope.symb.partition.trace;

import com.lexicalscope.symb.metastate.MetaKey;

public final class TraceMetaKey implements MetaKey<Trace> {
   public static final MetaKey<Trace> TRACE = new TraceMetaKey();

   private TraceMetaKey() { }

   @Override public Class<Trace> valueType() {
      return Trace.class;
   }
}
