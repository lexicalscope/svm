package com.lexicalscope.svm.vm.j.klass;

import com.lexicalscope.svm.vm.j.Instruction;


public interface SMethod {
   int maxLocals();

   int maxStack();

   Instruction entry();

   int argSize();

   SMethodDescriptor name();
}