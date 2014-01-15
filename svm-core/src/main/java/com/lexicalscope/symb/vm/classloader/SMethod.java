package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.InstructionNode;

public interface SMethod {
   int maxLocals();

   int maxStack();

   InstructionNode entry();

   int argSize();

   SMethodDescriptor name();
}