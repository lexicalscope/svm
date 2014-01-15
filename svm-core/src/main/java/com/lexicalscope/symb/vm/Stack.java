package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.heap.Heap;
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

   void query(Vop op, Statics statics, Heap heap);
   <T> T query(Op<T> op, Statics statics, Heap heap);

   int size();

   void currentThread(Object address);
   Object currentThread();

   SStackTrace trace();
}