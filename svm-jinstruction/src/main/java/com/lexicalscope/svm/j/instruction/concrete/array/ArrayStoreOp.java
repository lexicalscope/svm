package com.lexicalscope.svm.j.instruction.concrete.array;

import static com.lexicalscope.svm.j.instruction.concrete.array.NewArrayOp.*;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class ArrayStoreOp implements Vop {
   public interface ValueTransform {
      Object transformForStore(Object value);
   }

   private static ValueTransform noTransform = new ValueTransform() {
      @Override public Object transformForStore(final Object value) {
         return value;
      }
   };

   private static ValueTransform truncateToChar = new ValueTransform() {
      @Override public Object transformForStore(final Object value) {
         return (char)(int)value;
      }
   };

   private final ValueTransform valueTransform;


   public ArrayStoreOp(final ValueTransform valueTransform) {
      this.valueTransform = valueTransform;
   }

   @Override public void eval(final State ctx) {
      final Object value = ctx.pop();
      final int offset = (int) ctx.pop();
      final Object arrayref = ctx.pop();
      storeInHeap(ctx, arrayref, offset, value);
   }

   public void storeInHeap(final State ctx, final Object arrayref, final int offset, final Object value) {
      assert boundsCheck(ctx, arrayref, offset);
      ctx.put(arrayref, offset + ARRAY_PREAMBLE, valueTransform.transformForStore(value));
   }

   private boolean boundsCheck(final State ctx, final Object arrayref, final int offset) {
      return offset < (int) ctx.get(arrayref, ARRAY_LENGTH_OFFSET);
   }

   @Override public String toString() {
      return "ARRAYSTORE";
   }

   public static Vop caStore() {
      return new ArrayStoreOp(truncateToChar);
   }

   public static ArrayStoreOp aaStore() {
      return new ArrayStoreOp(noTransform);
   }

   public static ArrayStoreOp iaStore() {
      return new ArrayStoreOp(noTransform);
   }
}
