package com.lexicalscope.symb.vm.instructions.ops.array;

import static org.objectweb.asm.Type.getInternalName;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.classloader.Allocatable;
import com.lexicalscope.symb.vm.classloader.SClass;

public final class NewConcArray implements ArrayConstructor {
   public void newConcreteArray(
         final StackFrame stackFrame,
         final Heap heap,
         final Statics statics,
         final int arrayLength,
         final InitStrategy initStrategy) {
      final Object initValue = initStrategy.initialValue(heap);

      final Object arrayAddress = heap.newObject(new Allocatable() {
         @Override public int allocateSize() {
            return arrayLength + NewArrayOp.ARRAY_PREAMBLE;
         }
      });

      initArrayPreamble(heap, statics, arrayAddress, arrayLength);

      for (int i = 0; i < arrayLength; i++) {
         heap.put(arrayAddress, NewArrayOp.ARRAY_PREAMBLE + i, initValue);
      }
      stackFrame.push(arrayAddress);
   }

   public static void initArrayPreamble(final Heap heap, final Statics statics, final Object arrayAddress, final Object arrayLength) {
      // TODO - arrays can have different types
      final SClass objectArrayClass = statics.load(getInternalName(Object[].class));
      heap.put(arrayAddress, NewArrayOp.ARRAY_CLASS_OFFSET, objectArrayClass);
      heap.put(arrayAddress, NewArrayOp.ARRAY_LENGTH_OFFSET, arrayLength);
   }

   @Override public void newArray(final StackFrame stackFrame, final Heap heap, final Statics statics, final InitStrategy initStrategy) {
      newConcreteArray(stackFrame, heap, statics, (int) stackFrame.pop(), initStrategy);
   }
}