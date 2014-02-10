package com.lexicalscope.svm.partition.trace;

import com.lexicalscope.svm.metastate.MetaKey;

public final class TraceMetaKey implements MetaKey<Trace> {
   public static final MetaKey<Trace> TRACE = new TraceMetaKey();

   private TraceMetaKey() { }

   @Override public Class<Trace> valueType() {
      return Trace.class;
   }
}
