package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class LoadConstantArg implements Vop {
   private final Object value;

   public LoadConstantArg(final Object value) {
      assert value instanceof Integer || value instanceof ISymbol : value.getClass();
      this.value = value;
   }

   @Override public void eval(final Context ctx) {
      ctx.push(value);
   }
}