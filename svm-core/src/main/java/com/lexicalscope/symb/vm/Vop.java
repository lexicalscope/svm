package com.lexicalscope.symb.vm;



public interface Vop {
//   void eval(Vm<State> vm, Statics statics, Heap heap, Stack stack, StackFrame stackFrame, InstructionNode instructionNode);
   void eval(Context ctx);
}
