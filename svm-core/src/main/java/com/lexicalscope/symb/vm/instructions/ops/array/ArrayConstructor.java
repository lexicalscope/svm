package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.State;

public interface ArrayConstructor {
   void newArray(State ctx, InitStrategy initStrategy);
}
