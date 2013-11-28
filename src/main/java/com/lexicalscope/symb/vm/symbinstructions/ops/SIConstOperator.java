package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.symb.vm.instructions.NullaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;

public final class SIConstOperator implements NullaryOperator {
   private final IConstSymbol val;

   public SIConstOperator(final int val) {
      this.val = new IConstSymbol(val);
   }

   @Override
   public Object eval() {
      return val;
   }

   @Override
   public String toString() {
      return "ICONST_" + val;
   }
}