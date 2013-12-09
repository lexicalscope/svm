package com.lexicalscope.symb.vm;


public interface Instruction {
   void eval(Vm vm, State state);

   void next(Instruction instruction);
   void target(Instruction instruction);

   Instruction next();
   Instruction jmpTarget();
}
