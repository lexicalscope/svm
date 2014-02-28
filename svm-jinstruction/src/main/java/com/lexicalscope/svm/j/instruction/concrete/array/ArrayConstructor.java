package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.svm.vm.j.JState;

public interface ArrayConstructor {
   void newArray(JState ctx, InitStrategy initStrategy);
}
