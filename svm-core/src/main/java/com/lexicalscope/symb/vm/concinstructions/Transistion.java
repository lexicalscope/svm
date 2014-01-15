package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;

public interface Transistion {
   void next(State state, InstructionNode instruction);
}
