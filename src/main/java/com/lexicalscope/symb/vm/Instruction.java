package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClassLoader;

public interface Instruction {
   void eval(SClassLoader cl, Vm vm, State state);
}
