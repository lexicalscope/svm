package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.svm.vm.j.State;

public interface ArrayConstructor {
   void newArray(State ctx, InitStrategy initStrategy);
}
