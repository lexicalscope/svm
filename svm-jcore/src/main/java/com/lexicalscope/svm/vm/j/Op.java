package com.lexicalscope.svm.vm.j;



public interface Op<T> {
   T eval(JState ctx);
   <S> S query(InstructionQuery<S> instructionQuery);
}
