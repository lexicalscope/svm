package com.lexicalscope.symb.vm.j.j.klass;

import com.lexicalscope.symb.vm.j.Instruction;


public interface SMethod {
   int maxLocals();

   int maxStack();

   Instruction entry();

   int argSize();

   SMethodDescriptor name();
}