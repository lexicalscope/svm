package com.lexicalscope.symb.vm;


public interface Instruction {
   void eval(Vm<State> vm, State state, InstructionNode instruction);
}
