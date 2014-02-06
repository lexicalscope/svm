package com.lexicalscope.svm.j.instruction.concrete.object;

import static com.lexicalscope.symb.vm.j.j.klass.SClass.OBJECT_MARKER_OFFSET;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.Op;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public final class NewObjectOp implements Op<Object> {
   private final String klassDesc;

   public NewObjectOp(final String klassDesc) {
      this.klassDesc = klassDesc;
   }

   @Override public Object eval(final State ctx) {
      // TODO[tim]: linking should remove this
      final SClass klass = ctx.load(klassDesc);
      final Object address = allocateObject(ctx, klass);
      ctx.push(address);

      return address;
   }

   public static Object allocateObject(final Heap heap, final SClass klass) {
      final Object address = heap.newObject(klass);
      heap.put(address, OBJECT_MARKER_OFFSET, klass);

      final Object nullPointer = heap.nullPointer();
      int fieldOffset = OBJECT_MARKER_OFFSET + 1;
      for (final Object initialValue : klass.fieldInit()) {
         heap.put(address, fieldOffset, initialValue == null ? nullPointer : initialValue);
         fieldOffset++;
      }
      return address;
   }

   public static Object allocateObject(final State ctx, final SClass klass) {
      final Object address = ctx.newObject(klass);
      ctx.put(address, OBJECT_MARKER_OFFSET, klass);

      final Object nullPointer = ctx.nullPointer();
      int fieldOffset = OBJECT_MARKER_OFFSET + 1;
      for (final Object initialValue : klass.fieldInit()) {
         ctx.put(address, fieldOffset, initialValue == null ? nullPointer : initialValue);
         fieldOffset++;
      }
      return address;
   }

   @Override
   public String toString() {
      return String.format("NEW %s", klassDesc);
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.newobject(klassDesc);
   }
}