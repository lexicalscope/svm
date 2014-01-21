package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class ArrayLengthOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push(ctx.get(ctx.pop(), NewArrayOp.ARRAY_LENGTH_OFFSET));
   }

   @Override public String toString() {
      return "ARRAY_LENGTH";
   }
}