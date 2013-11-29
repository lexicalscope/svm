package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.State;

public interface StateTransformer {
   void transform(State state);
}
