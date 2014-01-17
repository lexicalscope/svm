package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.Context;

final class ReferenceInitStrategy implements InitStrategy {
   @Override public Object initialValue(final Context ctx) {
      return ctx.nullPointer();
   }
}