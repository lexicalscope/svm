package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;

public interface StateTransformer {
   void transform(State state, Instruction nextInstruction);
}
