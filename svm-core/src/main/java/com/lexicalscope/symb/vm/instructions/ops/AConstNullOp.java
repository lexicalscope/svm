package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class AConstNullOp implements Vop {
   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      stackFrame.push(heap.nullPointer());
   }

   @Override public String toString() {
      return "ACONST_NULL";
   }
}
