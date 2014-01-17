package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_LENGTH_OFFSET;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class ArrayLengthOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.push(ctx.get(ctx.pop(), ARRAY_LENGTH_OFFSET));
   }

   @Override public String toString() {
      return "ARRAY_LENGTH";
   }
}
