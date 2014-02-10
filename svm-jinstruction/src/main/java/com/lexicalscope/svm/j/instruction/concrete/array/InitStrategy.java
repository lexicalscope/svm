package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.svm.vm.j.State;


public interface InitStrategy {
   Object initialValue(State ctx);
}