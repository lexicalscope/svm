package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.classloader.SClass.OBJECT_CLASS_OFFSET;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public class InstanceOfOp implements Vop {
   private final String klassName;

   public InstanceOfOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object address = stackFrame.pop();
      if(!heap.nullPointer().equals(address)) {
         stackFrame.push(1);
         return;
      }

      final SClass classFromHeap = (SClass) heap.get(address, OBJECT_CLASS_OFFSET);
      final SClass classFromInstruction = statics.load(klassName);

      if(classFromHeap.instanceOf(classFromInstruction)) {
         stackFrame.push(1);
         return;
      }
      stackFrame.push(0);
   }
}
