package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_LENGTH_OFFSET;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class ArrayLengthOp implements Vop {
   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      final Object address = stackFrame.pop();
      stackFrame.push(heap.get(address, ARRAY_LENGTH_OFFSET));
   }

   @Override public String toString() {
      return "ARRAY_LENGTH";
   }
}
