package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class AddressToHashCodeOp implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object address = stackFrame.pop();
      stackFrame.push(heap.hashCode(address));
   }

   @Override public String toString() {
      return "ADDRESS TO HASHCODE";
   }
}
