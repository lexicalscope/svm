package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.Context;

final class PrimitiveInitStrategy implements InitStrategy {
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

   @Override public Object initialValue(final Context ctx) {
      return initialValue;
   }
}