package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class AddressToHashCodeOp implements Vop {
   @Override public void eval(final Context ctx) {
      ctx.push(ctx.hashCode(ctx.pop()));
   }

   @Override public String toString() {
      return "ADDRESS TO HASHCODE";
   }
}
