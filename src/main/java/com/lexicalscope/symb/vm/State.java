package com.lexicalscope.symb.vm;

public interface State extends Snapshotable<State>{
   State[] fork();

   <T> T op(Op<T> op);
   State op(Vop op);

   <T> T op(StackOp<T> op);
   State op(StackVop op);

   void advance(Vm vm);

   Object getMeta();
}