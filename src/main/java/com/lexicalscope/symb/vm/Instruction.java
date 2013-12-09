package com.lexicalscope.symb.vm;


public interface Instruction {
   void eval(Vm vm, State state);

   void next(Instruction instruction);
   void jmpTarget(Instruction instruction);

   Instruction next();
   Instruction jmpTarget();
}
