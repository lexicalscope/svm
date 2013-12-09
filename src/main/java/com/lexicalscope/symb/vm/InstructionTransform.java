package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClassLoader;

public interface InstructionTransform {
   void eval(SClassLoader cl, Vm vm, State state, Instruction instruction);
}
