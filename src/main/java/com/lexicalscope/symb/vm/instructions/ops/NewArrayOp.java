package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.Allocatable;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;

public class NewArrayOp implements Vop {
   private interface InitStrategy {
      Object initialValue(Heap heap);
   }

   private static final class PrimitiveInitStrategy implements InitStrategy {
      private final Object initialValue;

      public PrimitiveInitStrategy(final Object initialValue) {
         assert initialValue instanceof Integer ||
         initialValue instanceof Long ||
         initialValue instanceof Float ||
         initialValue instanceof Double ||
         initialValue instanceof Character ||
         initialValue instanceof Short ||
         initialValue instanceof Byte : initialValue;

      this.initialValue = initialValue;
      }

      @Override public Object initialValue(final Heap heap) {
         return initialValue;
      }
   }

   private static final class ReferenceInitStrategy implements InitStrategy {
      @Override public Object initialValue(final Heap heap) {
         return heap.nullPointer();
      }
   }

   static final int ARRAY_LENGTH_OFFSET = 0;
   public static final int ARRAY_PREAMBLE = 1;
   private final InitStrategy initStrategy;

   public NewArrayOp() {
      this(new ReferenceInitStrategy());
   }

   public NewArrayOp(final Object initialValue) {
      this(new PrimitiveInitStrategy(initialValue));
   }

   private NewArrayOp(final InitStrategy initStrategy) {
      this.initStrategy = initStrategy;
   }

   // TODO - arrays can have different types
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object initValue = initStrategy.initialValue(heap);

      final Object top = stackFrame.pop();
      // need to deal with symbolic lengths
      final int arrayLength;
      if(top instanceof IConstSymbol) {
         arrayLength = ((IConstSymbol) top).val();
      } else {
         arrayLength = (int) top;
      }

      final Object arrayAddress = heap.newObject(new Allocatable() {
         @Override public int fieldCount() {
            return arrayLength + ARRAY_PREAMBLE;
         }
      });
      heap.put(arrayAddress, ARRAY_LENGTH_OFFSET, arrayLength);
      for (int i = 0; i < arrayLength; i++) {
         heap.put(arrayAddress, ARRAY_PREAMBLE + i, initValue);
      }
      stackFrame.push(arrayAddress);
   }

   @Override public String toString() {
      return "NEWARRAY";
   }
}
