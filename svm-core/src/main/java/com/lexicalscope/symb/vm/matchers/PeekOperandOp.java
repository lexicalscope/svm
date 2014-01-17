package com.lexicalscope.symb.vm.matchers;

import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;

final class PeekOperandOp implements Op<Object> {
   @Override public Object eval(final State ctx) {
      return ctx.peek();
   }
}