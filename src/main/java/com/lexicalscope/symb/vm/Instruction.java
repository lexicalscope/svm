package com.lexicalscope.symb.vm;

public interface Instruction {
   State eval(Vm vm, State state);
}
