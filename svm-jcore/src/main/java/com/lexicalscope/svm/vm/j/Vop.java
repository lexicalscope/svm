package com.lexicalscope.svm.vm.j;


public interface Vop {
   void eval(State ctx);
   <T> T query(InstructionQuery<T> instructionQuery);
}
