package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class AddressToHashCodeOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.push(ctx.hashCode(ctx.pop()));
   }

   @Override public String toString() {
      return "ADDRESS TO HASHCODE";
   }
}
