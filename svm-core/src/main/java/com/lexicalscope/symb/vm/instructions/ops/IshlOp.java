package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class IshlOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      final int value2 = (int) ctx.pop();
      final int value1 = (int) ctx.pop();

      ctx.push(value1 << (value2 & 0x1f));
   }

   @Override public String toString() {
      return "ISHL";
   }
}
