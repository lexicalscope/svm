package com.lexicalscope.svm.j.instruction.concrete;

import static com.lexicalscope.svm.j.instruction.concrete.DefineClassOp.primitivesContains;

import com.lexicalscope.svm.j.instruction.concrete.array.NewArrayOp;
import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.SClass;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public final class GetPrimitiveClass implements Vop {
   @Override public void eval(final State ctx) {
      final Object primitiveNameRef = ctx.pop();
      final String klassName = inGameStringToRealLifeString(ctx, primitiveNameRef);
      assert primitivesContains(klassName) : klassName + " is not a primitive";
      ctx.push(ctx.whereMyClassAt(klassName));
   }

   private String inGameStringToRealLifeString(final State ctx, final Object primitiveNameRef) {
      final SClass string = ctx.load(JavaConstants.STRING_CLASS);
      final int valueFieldIndex = string.fieldIndex(JavaConstants.STRING_VALUE_FIELD);
      return new String(extractCharArray(ctx, primitiveNameRef, valueFieldIndex)).intern();
   }

   private char[] extractCharArray(final State ctx, final Object primitiveNameRef, final int valueFieldIndex) {
      final Object arrayPointer = ctx.get(primitiveNameRef, valueFieldIndex);
      final Object[] array = extractArray(ctx, arrayPointer);
      return toCharArray(array);
   }

   private Object[] extractArray(final State ctx, final Object arrayPointer) {
      final int arrayLength = (int) ctx.get(arrayPointer, NewArrayOp.ARRAY_LENGTH_OFFSET);
      final Object[] result = new Object[arrayLength];
      for (int i = 0; i < arrayLength; i++) {
         result[i] = ctx.get(arrayPointer, NewArrayOp.ARRAY_PREAMBLE + i);
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
