package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.InstructionNode;

public interface SMethod {
   SClass klass();

   int maxLocals();

   int maxStack();

   InstructionNode entry();

   int argSize();

   SMethodName name();
}