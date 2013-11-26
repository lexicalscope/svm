package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.symb.vm.instructions.NullaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ConstSymbol;

public final class SIConstM1Operator implements NullaryOperator {
   @Override
   public Object eval() {
      return new ConstSymbol(-1);
   }

   @Override
   public String toString() {
      return "ICONST_M!";
   }
}