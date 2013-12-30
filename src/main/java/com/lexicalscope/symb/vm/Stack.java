package com.lexicalscope.symb.vm;


public interface Stack extends Snapshotable<Stack> {
   /**
    * @return The previous stack frame
    */
   StackFrame caller();

   Stack popFrame(int returnCount);

   Stack push(StackFrame stackFrame);

   InstructionNode instruction();

   void query(Vop op, Statics statics, Heap heap);
   <T> T query(Op<T> op, Statics statics, Heap heap);

   int size();

   void currentThread(Object address);
   Object currentThread();

   SStackTrace trace();

}