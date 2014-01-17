package com.lexicalscope.svm.j.instruction.concrete;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.State;
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

   @Override public void eval(final State ctx) {
      final int offset = (int) ctx.pop();
      final Object arrayref = ctx.pop();

      ctx.push(loadFromHeap(ctx, arrayref, offset));

   }

   public Object loadFromHeap(final State ctx, final Object arrayref, final int offset) {
      // TODO[tim]: check bounds in-game
      assert inBounds(ctx, offset, arrayref) : String.format("out-of-bounds %d %s %s", offset, arrayref, ctx);

      return valueTransform.transformForLoad(ctx.get(arrayref, offset + ARRAY_PREAMBLE));
   }

   private boolean inBounds(final State ctx, final int offset, final Object arrayref) {
      return offset >= 0 && offset < (int) ctx.get(arrayref, NewArrayOp.ARRAY_LENGTH_OFFSET);
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
