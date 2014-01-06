package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.DefineClassOp.primitivesContains;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public final class GetPrimitiveClass implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object primitiveNameRef = stackFrame.pop();
      final String klassName = inGameStringToRealLifeString(statics, heap, primitiveNameRef);
      assert primitivesContains(klassName) : klassName + " is not a primitive";
      stackFrame.push(statics.whereMyClassAt(klassName));
   }

   private String inGameStringToRealLifeString(final Statics statics, final Heap heap, final Object primitiveNameRef) {
      final SClass string = statics.load(JavaConstants.STRING_CLASS);
      final int valueFieldIndex = string.fieldIndex(JavaConstants.STRING_VALUE_FIELD);
      return new String(extractCharArray(heap, primitiveNameRef, valueFieldIndex)).intern();
   }

   private char[] extractCharArray(final Heap heap, final Object primitiveNameRef, final int valueFieldIndex) {
      final Object arrayPointer = heap.get(primitiveNameRef, valueFieldIndex);
      final Object[] array = extractArray(heap, arrayPointer);
      return toCharArray(array);
   }

   private Object[] extractArray(final Heap heap, final Object arrayPointer) {
      final int arrayLength = (int) heap.get(arrayPointer, NewArrayOp.ARRAY_LENGTH_OFFSET);
      final Object[] result = new Object[arrayLength];
      for (int i = 0; i < arrayLength; i++) {
         result[i] = heap.get(arrayPointer, NewArrayOp.ARRAY_PREAMBLE + i);
      }
      return result;
   }

   private char[] toCharArray(final Object[] array) {
      final char[] result = new char[array.length];
      for (int i = 0; i < result.length; i++) {
         result[i] = (char) array[i];
      }
      return result;
   }
}
