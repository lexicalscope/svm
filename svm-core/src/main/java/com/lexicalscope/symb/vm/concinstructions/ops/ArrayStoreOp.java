package com.lexicalscope.symb.vm.concinstructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.*;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
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

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      final Object value = stackFrame.pop();
      final int offset = (int) stackFrame.pop();
      final Object arrayref = stackFrame.pop();
      storeInHeap(heap, arrayref, offset, value);
   }

   public void storeInHeap(final Heap heap, final Object arrayref, final int offset, final Object value) {
      assert boundsCheck(heap, arrayref, offset);
      heap.put(arrayref, offset + ARRAY_PREAMBLE, valueTransform.transformForStore(value));
   }

   private boolean boundsCheck(final Heap heap, final Object arrayref, final int offset) {
      return offset < (int) heap.get(arrayref, ARRAY_LENGTH_OFFSET);
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
