package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;

public interface Transistion {
   void next(State state, Instruction instruction);
}
