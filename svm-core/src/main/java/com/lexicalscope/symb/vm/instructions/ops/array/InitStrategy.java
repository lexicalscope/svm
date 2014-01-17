package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.Context;


public interface InitStrategy {
   Object initialValue(Context ctx);
}