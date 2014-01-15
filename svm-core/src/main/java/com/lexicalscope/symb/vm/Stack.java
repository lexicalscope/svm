package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.stack.trace.SStackTrace;
import com.lexicalscope.symb.state.Snapshotable;


public interface Stack extends Snapshotable<Stack> {
   /**
    * @return The previous stack frame
    */
   StackFrame caller();

   Stack popFrame(int returnCount);

   Stack push(StackFrame stackFrame);

   // TODO[tim]: does this really advance?
   void advance(Vm vm, State state);

   void query(StackVop op);
   <T> T query(StackOp<T> op);

   int size();

   void currentThread(Object address);
   Object currentThread();

   SStackTrace trace();
}