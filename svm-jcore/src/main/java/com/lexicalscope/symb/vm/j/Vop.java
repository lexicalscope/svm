package com.lexicalscope.symb.vm.j;


public interface Vop {
   void eval(State ctx);
   <T> T query(InstructionQuery<T> instructionQuery);
}
