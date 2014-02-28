package com.lexicalscope.svm.partition.trace.ops;

import static com.lexicalscope.svm.partition.trace.CrossingCallMetaKey.CROSSINGCALL;
import static com.lexicalscope.svm.partition.trace.TraceMetaKey.TRACE;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.partition.trace.InstrumentationContext;
import com.lexicalscope.svm.partition.trace.Trace.CallReturn;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class TraceCallbackCallOp implements Vop {
   private final Matcher<? super InstrumentationContext> callbackMatcher;
   private final SMethodDescriptor methodName;

   public TraceCallbackCallOp(final SMethodDescriptor methodName, final Matcher<? super InstrumentationContext> callbackMatcher) {
      this.methodName = methodName;
      this.callbackMatcher = callbackMatcher;
   }

   @Override public void eval(final JState ctx) {
      final InstrumentationContext instrumentationContext = new InstrumentationContext(methodName, ctx);
      if(callbackMatcher.matches(instrumentationContext)) {
         ctx.setMeta(TRACE, ctx.getMeta(TRACE).extend(methodName, CallReturn.CALL, ctx.peek(methodName.argSize())));
         ctx.currentFrame().setMeta(CROSSINGCALL, true);
         ctx.goal();
      }
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.synthetic();
   }
}
