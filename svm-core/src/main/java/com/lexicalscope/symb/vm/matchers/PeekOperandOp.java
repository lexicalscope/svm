package com.lexicalscope.symb.vm.matchers;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Op;

final class PeekOperandOp implements Op<Object> {
   @Override public Object eval(final Context ctx) {
      return ctx.peek();
   }
}