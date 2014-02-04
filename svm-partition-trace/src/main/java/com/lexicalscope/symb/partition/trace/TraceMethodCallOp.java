package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class TraceMethodCallOp implements Vop {
   @Override public void eval(final State ctx) {
      final Trace trace = ctx.getMeta(TRACE);
      ctx.setMeta(TRACE, trace.extend(ctx.currentFrame()));
   }

   @Override public String toString() {
      return "TRACE method call";
   }
}
