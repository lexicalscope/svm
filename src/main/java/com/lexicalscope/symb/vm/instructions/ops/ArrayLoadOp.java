package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class ArrayLoadOp implements Vop {
   public interface ValueTransform {
      Object transformForStore(Object value);
   }

   private static ValueTransform noTransform = new ValueTransform() {
      @Override public Object transformForStore(final Object value) {
         return value;
      }
   };

   private static ValueTransform widenToInt = new ValueTransform() {
      @Override public Object transformForStore(final Object value) {
         assert value instanceof Character;
         return (int)(char)value;
      }
   };

   private final ValueTransform valueTransform;

   public ArrayLoadOp(final ValueTransform valueTransform) {
      this.valueTransform = valueTransform;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final int offset = (int) stackFrame.pop();
      final Object arrayref = stackFrame.pop();

      stackFrame.push(valueTransform.transformForStore(heap.get(arrayref, offset + ARRAY_PREAMBLE)));
   }

   @Override public String toString() {
      return "ARRAYLOAD";
   }

   public static Vop caLoad() {
      return new ArrayLoadOp(widenToInt);
   }

   public static Vop aaLoad() {
      return new ArrayLoadOp(noTransform);
   }
}
