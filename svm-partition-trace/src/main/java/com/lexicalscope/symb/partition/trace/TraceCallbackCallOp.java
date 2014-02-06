package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;

import org.hamcrest.Matcher;

import com.lexicalscope.symb.partition.trace.Trace.CallReturn;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class TraceCallbackCallOp implements Vop {
   private final Matcher<? super InstrumentationContext> callbackMatcher;
   private final Instruction instruction;

   public TraceCallbackCallOp(final Instruction instruction, final Matcher<? super InstrumentationContext> callbackMatcher) {
      this.instruction = instruction;
      this.callbackMatcher = callbackMatcher;
   }

   @Override public void eval(final State ctx) {
      final InstrumentationContext instrumentationContext = new InstrumentationContext(instruction, ctx);
      if(callbackMatcher.matches(instrumentationContext)) {
         final Trace trace = ctx.getMeta(TRACE);
         ctx.setMeta(TRACE, trace.extend(instrumentationContext.methodCalled(), CallReturn.CALL));
      }
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.synthetic();
   }
}
