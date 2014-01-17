package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.State;


public interface InitStrategy {
   Object initialValue(State ctx);
}