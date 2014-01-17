package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.Context;

public interface ArrayConstructor {
   void newArray(Context ctx, InitStrategy initStrategy);
}
