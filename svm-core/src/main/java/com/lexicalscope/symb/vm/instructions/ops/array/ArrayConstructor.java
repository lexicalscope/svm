package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.StateImpl;

public interface ArrayConstructor {
   void newArray(StateImpl ctx, InitStrategy initStrategy);
}
