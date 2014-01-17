package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class Dup_X1Op implements Vop {
   @Override
   public String toString() {
      return "DUP_X1";
   }

   @Override public void eval(final StateImpl ctx) {
      final Object value1 = ctx.pop();
      final Object value2 = ctx.pop();
      ctx.push(value1);
      ctx.push(value2);
      ctx.push(value1);
   }
}
