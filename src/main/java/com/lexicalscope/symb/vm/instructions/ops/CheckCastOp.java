package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.classloader.SClass.OBJECT_CLASS_OFFSET;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public class CheckCastOp implements Vop {
   private final String klassName;

   public CheckCastOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object address = stackFrame.peek();
      if(!heap.nullPointer().equals(address)) {
         return;
      }

      final SClass classFromHeap = (SClass) heap.get(address, OBJECT_CLASS_OFFSET);
      final SClass classFromInstruction = statics.load(klassName);

      if(classFromHeap.instanceOf(classFromInstruction)) {
         return;
      }

      stackFrame.pop();

      // TODO[tim]: should throw an in-game class cast exception
      throw new UnsupportedOperationException();
   }
}
