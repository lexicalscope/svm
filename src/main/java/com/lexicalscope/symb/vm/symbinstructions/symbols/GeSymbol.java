package com.lexicalscope.symb.vm.symbinstructions.symbols;

public class GeSymbol implements Symbol {
   private final Symbol val;

   public GeSymbol(final Symbol val) {
      this.val = val;
   }

   @Override
   public String toString() {
      return String.format("(>= %s 0)", val);
   }
}
