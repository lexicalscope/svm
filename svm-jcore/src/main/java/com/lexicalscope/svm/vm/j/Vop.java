package com.lexicalscope.svm.vm.j;


public interface Vop {
   void eval(JState ctx);
   <T> T query(InstructionQuery<T> instructionQuery);
}
