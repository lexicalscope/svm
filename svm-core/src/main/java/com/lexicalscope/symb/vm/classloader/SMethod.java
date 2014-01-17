package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.Instruction;

public interface SMethod {
   int maxLocals();

   int maxStack();

   Instruction entry();

   int argSize();

   SMethodDescriptor name();
}