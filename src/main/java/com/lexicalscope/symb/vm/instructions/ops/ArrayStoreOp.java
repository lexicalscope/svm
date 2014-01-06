package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
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

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object value = stackFrame.pop();
      final int offset = (int) stackFrame.pop();
      final Object arrayref = stackFrame.pop();

      heap.put(arrayref, offset + ARRAY_PREAMBLE, valueTransform.transformForStore(value));
   }

   @Override public String toString() {
      return "ARRAYSTORE";
   }

   public static Vop caStore() {
      return new ArrayStoreOp(truncateToChar);
   }

   public static Vop aaStore() {
      return new ArrayStoreOp(noTransform);
   }
}
