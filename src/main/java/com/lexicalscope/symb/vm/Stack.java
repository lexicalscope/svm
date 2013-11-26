package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public interface Stack {

   Stack popFrame(int returnCount);

   Stack pushFrame(Instruction returnTo, SMethod method, int argCount);

   Instruction instruction();

   <T> T query(StackFrameOp<T> op);

   int size();

   Stack snapshot();

}