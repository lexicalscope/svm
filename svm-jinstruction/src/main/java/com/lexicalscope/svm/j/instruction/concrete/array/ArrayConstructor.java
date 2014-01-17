package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.symb.vm.State;

public interface ArrayConstructor {
   void newArray(State ctx, InitStrategy initStrategy);
}
