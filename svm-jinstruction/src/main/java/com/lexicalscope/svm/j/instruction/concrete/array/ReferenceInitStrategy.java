package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.svm.vm.j.JState;

final class ReferenceInitStrategy implements InitStrategy {
   @Override public Object initialValue(final JState ctx) {
      return ctx.nullPointer();
   }
}