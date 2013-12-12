package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.classloader.SClass.OBJECT_CLASS_OFFSET;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public final class NewOp implements Vop {
   private final String klassDesc;

   public NewOp(final String klassDesc) {
      this.klassDesc = klassDesc;
   }

   @Override
   public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      // TODO[tim]: linking should remove this
      final SClass klass = statics.load(klassDesc);
      final Object address = heap.newObject(klass);
      heap.put(address, OBJECT_CLASS_OFFSET, klass);
      stackFrame.push(address);
   }

   @Override
   public String toString() {
      return String.format("NEW %s", klassDesc);
   }
}