package com.lexicalscope.symb.vm;


public interface SMethod {
   int maxLocals();

   int maxStack();

   Instruction entry();

   int argSize();

   SMethodDescriptor name();
}