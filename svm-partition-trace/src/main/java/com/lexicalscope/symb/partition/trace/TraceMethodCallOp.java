package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.partition.trace.Trace.CallReturn.CALL;
import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;

import org.hamcrest.Matcher;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class TraceMethodCallOp implements Vop {
   private final Matcher<? super State> matcher;

   public TraceMethodCallOp(final Matcher<? super State> matcher) {
      this.matcher = matcher;
   }

   @Override public void eval(final State ctx) {
      if(matcher.matches(ctx)) {
         final Trace trace = ctx.getMeta(TRACE);
         ctx.setMeta(TRACE, trace.extend(ctx.currentFrame(), CALL));
      }
   }

   @Override public String toString() {
      return "TRACE method call";
   }
}
