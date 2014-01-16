package com.lexicalscope.symb.stack;

import com.lexicalscope.symb.stack.trace.SStackTrace;
import com.lexicalscope.symb.state.Snapshotable;


public interface Stack extends Snapshotable<Stack> {
   Stack popFrame(int returnCount);
   Stack push(StackFrame stackFrame);

   StackFrame topFrame();
   StackFrame previousFrame();

   void query(StackVop op);
   <T> T query(StackOp<T> op);

   int size();

   void currentThread(Object address);
   Object currentThread();

   SStackTrace trace();
}