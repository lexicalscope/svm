package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.svm.vm.j.JState;


public interface InitStrategy {
   Object initialValue(JState ctx);
}