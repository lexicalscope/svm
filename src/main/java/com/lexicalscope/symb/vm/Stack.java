package com.lexicalscope.symb.vm;


public interface Stack extends Snapshotable<Stack> {
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