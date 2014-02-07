package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.partition.trace.CrossingCallMetaKey.CROSSINGCALL;
import static com.lexicalscope.symb.partition.trace.Trace.CallReturn.RETURN;
import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class TraceCallbackReturnOp implements Vop {
   private final SMethodDescriptor methodName;

   public TraceCallbackReturnOp(final SMethodDescriptor methodName) {
      this.methodName = methodName;
   }

   @Override public void eval(final State ctx) {
      if(ctx.currentFrame().containsMeta(CROSSINGCALL)) {
         ctx.currentFrame().removeMeta(CROSSINGCALL);
         ctx.setMeta(TRACE, ctx.getMeta(TRACE).extend(methodName, RETURN));
      }
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.synthetic();
   }
}
