package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.symb.vm.State;


public interface InitStrategy {
   Object initialValue(State ctx);
}