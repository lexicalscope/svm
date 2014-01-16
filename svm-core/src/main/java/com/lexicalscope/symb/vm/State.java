package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.stack.trace.SStackTrace;
import com.lexicalscope.symb.state.Snapshotable;

public interface State extends Snapshotable<State>, FlowNode<State> {
   State[] fork();

   StackFrame stackFrame();
   Object getMeta();

   // do op on VM instead to avoid passing state around.
   <T> T op(Op<T> op, Vm<State> vm);
   State op(Vop op, Vm<State> vm);

   SStackTrace trace();
}