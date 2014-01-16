package com.lexicalscope.symb.vm;

public interface ExecutableState<S extends ExecutableState<S>> {
   void executeNextInstruction(Vm<S> vm);
}
