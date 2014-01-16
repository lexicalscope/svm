package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class Dup_X1Op implements Vop {
   @Override
   public String toString() {
      return "DUP_X1";
   }

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      final Object value1 = stackFrame.pop();
      final Object value2 = stackFrame.pop();
      stackFrame.push(value1);
      stackFrame.push(value2);
      stackFrame.push(value1);
   }
}
