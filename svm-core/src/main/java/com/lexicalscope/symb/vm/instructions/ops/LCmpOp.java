package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class LCmpOp implements Vop {
   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      final long value2 = (long) stackFrame.popDoubleWord();
      final long value1 = (long) stackFrame.popDoubleWord();

      Object result;
      if(value1 > value2) {
         result = 1;
      } else if(value1 < value2) {
         result = -1;
      } else {
         result = 0;
      }
      stackFrame.push(result);
   }

   @Override public String toString() {
      return "LCMP";
   }
}
