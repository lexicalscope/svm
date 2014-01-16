package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.classloader.SClass.OBJECT_MARKER_OFFSET;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClass;

public final class NewObjectOp implements Op<Object> {
   private final String klassDesc;

   public NewObjectOp(final String klassDesc) {
      this.klassDesc = klassDesc;
   }

   @Override
   public Object eval(Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      // TODO[tim]: linking should remove this
      final SClass klass = statics.load(klassDesc);
      final Object address = allocateObject(heap, klass);
      stackFrame.push(address);

      return address;
   }

   public static Object allocateObject(final Heap heap, final SClass klass) {
      final Object address = heap.newObject(klass);
      heap.put(address, OBJECT_MARKER_OFFSET, klass);

      final Object nullPointer = heap.nullPointer();
      int fieldOffset = OBJECT_MARKER_OFFSET + 1;
      for (final Object initialValue : klass.fieldInit()) {
         heap.put(address, fieldOffset, initialValue == null ? nullPointer : initialValue);
         fieldOffset++;
      }
      return address;
   }

   @Override
   public String toString() {
      return String.format("NEW %s", klassDesc);
   }
}