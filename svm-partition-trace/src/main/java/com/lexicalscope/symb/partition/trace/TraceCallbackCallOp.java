package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.partition.trace.CrossingCallMetaKey.CROSSINGCALL;
import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;

import org.hamcrest.Matcher;

import com.lexicalscope.symb.partition.trace.Trace.CallReturn;
import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class TraceCallbackCallOp implements Vop {
   private final Matcher<? super InstrumentationContext> callbackMatcher;
   private final SMethodDescriptor methodName;

   public TraceCallbackCallOp(final SMethodDescriptor methodName, final Matcher<? super InstrumentationContext> callbackMatcher) {
      this.methodName = methodName;
      this.callbackMatcher = callbackMatcher;
   }

   @Override public void eval(final State ctx) {
      final InstrumentationContext instrumentationContext = new InstrumentationContext(methodName, ctx);
      if(callbackMatcher.matches(instrumentationContext)) {
         ctx.setMeta(TRACE, ctx.getMeta(TRACE).extend(methodName, CallReturn.CALL));
         ctx.currentFrame().setMeta(CROSSINGCALL, true);
      }
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.synthetic();
   }
}
