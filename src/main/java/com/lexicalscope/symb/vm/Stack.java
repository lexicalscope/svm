package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public interface Stack extends Snapshotable<Stack> {
   Stack popFrame(int returnCount);

   Stack pushFrame(InstructionNode returnTo, SMethod method, int argCount);

   InstructionNode instruction();

   <T> T query(StackFrameOp<T> op);

   int size();

   @Override
   Stack snapshot();
}