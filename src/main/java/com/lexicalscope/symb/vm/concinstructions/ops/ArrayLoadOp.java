package com.lexicalscope.symb.vm.concinstructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp;

public class ArrayLoadOp implements Vop {
   public interface ValueTransform {
      Object transformForLoad(Object value);
   }

   private static ValueTransform noTransform = new ValueTransform() {
      @Override public Object transformForLoad(final Object value) {
         return value;
      }
   };

   private static ValueTransform widenToInt = new ValueTransform() {
      @Override public Object transformForLoad(final Object value) {
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

      stackFrame.push(loadFromHeap(heap, arrayref, offset));
   }

   public Object loadFromHeap(final Heap heap, final Object arrayref, final int offset) {
      // TODO[tim]: check bounds in-game
      assert inBounds(heap, offset, arrayref) : String.format("out-of-bounds %d %s %s", offset, arrayref, heap);

      return valueTransform.transformForLoad(heap.get(arrayref, offset + ARRAY_PREAMBLE));
   }

   private boolean inBounds(final Heap heap, final int offset, final Object arrayref) {
      return offset >= 0 && offset < (int) heap.get(arrayref, NewArrayOp.ARRAY_LENGTH_OFFSET);
   }

   @Override public String toString() {
      return "ARRAYLOAD";
   }

   public static ArrayLoadOp caLoad() {
      return new ArrayLoadOp(widenToInt);
   }

   public static ArrayLoadOp aaLoad() {
      return new ArrayLoadOp(noTransform);
   }

   public static ArrayLoadOp iaLoad() {
      return new ArrayLoadOp(noTransform);
   }
}