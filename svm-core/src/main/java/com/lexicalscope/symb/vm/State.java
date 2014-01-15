package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.stack.trace.SStackTrace;
import com.lexicalscope.symb.state.Snapshotable;

public interface State extends Snapshotable<State>{
   State[] fork();

   <T> T op(Op<T> op);
   State op(Vop op);

   <T> T op(StackOp<T> op);
   State op(StackVop op);

   void advance(Vm vm);

   Object getMeta();

   SStackTrace trace();
}