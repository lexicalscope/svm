package com.lexicalscope.symb.vm.matchers;

import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.StateImpl;

final class PeekOperandOp implements Op<Object> {
   @Override public Object eval(final StateImpl ctx) {
      return ctx.peek();
   }
}