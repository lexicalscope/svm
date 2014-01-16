package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.stack.trace.SStackTrace;
import com.lexicalscope.symb.state.Snapshotable;

public interface State extends Snapshotable<State>, ExecutableState<State> {
   State[] fork();

   <T> T op(Op<T> op);
   State op(Vop op);

   Object getMeta();

   SStackTrace trace();
}