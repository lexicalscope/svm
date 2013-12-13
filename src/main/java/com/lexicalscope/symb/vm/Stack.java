package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SMethod;

public interface Stack extends Snapshotable<Stack> {
   Stack popFrame(int returnCount);

   Stack pushFrame(InstructionNode returnTo, SMethod method, int argCount);

   InstructionNode instruction();

   void query(Vop op, Statics statics, Heap heap);
   <T> T query(Op<T> op, Statics statics, Heap heap);

   int size();

   void currentThread(Object address);
   Object currentThread();

   @Override
   Stack snapshot();
}