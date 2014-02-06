package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;

import org.hamcrest.Matcher;

import com.lexicalscope.symb.partition.trace.Trace.CallReturn;
import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class TraceMethodCallOp implements Vop {
   private final Matcher<? super State> matcher;
   private final CallReturn callReturn;

   public TraceMethodCallOp(final Matcher<? super State> matcher, final CallReturn callReturn) {
      this.matcher = matcher;
      this.callReturn = callReturn;
   }

   @Override public void eval(final State ctx) {
      if(matcher.matches(ctx)) {
         final Trace trace = ctx.getMeta(TRACE);
         ctx.setMeta(TRACE, trace.extend(ctx.currentFrame(), callReturn));
      }
   }

   @Override public String toString() {
      return "TRACE method " + callReturn;
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.synthetic();
   }
}
