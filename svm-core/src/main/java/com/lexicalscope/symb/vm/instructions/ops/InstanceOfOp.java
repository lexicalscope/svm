package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.classloader.SClass.OBJECT_MARKER_OFFSET;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public class InstanceOfOp implements Vop {
   private final String klassName;

   public InstanceOfOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      final Object address = stackFrame.pop();
      if(!heap.nullPointer().equals(address)) {
         stackFrame.push(1);
         return;
      }

      final SClass classFromHeap = (SClass) heap.get(address, OBJECT_MARKER_OFFSET);
      final SClass classFromInstruction = statics.load(klassName);

      if(classFromHeap.instanceOf(classFromInstruction)) {
         stackFrame.push(1);
         return;
      }
      stackFrame.push(0);
   }

   @Override public String toString() {
      return "INSTANCEOF";
   }
}
