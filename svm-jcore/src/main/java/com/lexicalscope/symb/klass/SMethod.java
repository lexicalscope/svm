package com.lexicalscope.symb.klass;

import com.lexicalscope.symb.vm.Instruction;


public interface SMethod {
   int maxLocals();

   int maxStack();

   Instruction entry();

   int argSize();

   SMethodDescriptor name();
}