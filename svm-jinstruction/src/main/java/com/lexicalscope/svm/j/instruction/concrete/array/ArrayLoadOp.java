package com.lexicalscope.svm.j.instruction.concrete.array;

import static com.lexicalscope.svm.j.instruction.concrete.array.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

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

   @Override public void eval(final JState ctx) {
      final int offset = (int) ctx.pop();
      final ObjectRef arrayref = (ObjectRef) ctx.pop();

      ctx.push(loadFromHeap(ctx, arrayref, offset));

   }

   public Object loadFromHeap(final JState ctx, final ObjectRef arrayref, final int offset) {
      // TODO[tim]: check bounds in-game
      assert inBounds(ctx, offset, arrayref) : String.format("out-of-bounds %d %s %s", offset, arrayref, ctx);

      return valueTransform.transformForLoad(ctx.get(arrayref, offset + ARRAY_PREAMBLE));
   }

   private boolean inBounds(final JState ctx, final int offset, final ObjectRef arrayref) {
      return offset >= 0 && offset < (int) ctx.get(arrayref, NewArrayOp.ARRAY_LENGTH_OFFSET);
   }

   @Override public String toString() {
      return "ARRAYLOAD";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.arrayload();
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
