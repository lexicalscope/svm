package com.lexicalscope.symb.vm;


public interface Instruction {
   void eval(Vm vm, State state, InstructionNode instruction);
}
