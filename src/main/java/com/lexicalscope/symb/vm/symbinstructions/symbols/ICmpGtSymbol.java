package com.lexicalscope.symb.vm.symbinstructions.symbols;

public class ICmpGtSymbol implements ISymbol {
   private final ISymbol value1;
   private final ISymbol value2;

   public ICmpGtSymbol(final ISymbol value1, final ISymbol value2) {
      this.value1 = value1;
      this.value2 = value2;
   }

   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.gt(value1, value2);
   }

   @Override
   public String toString() {
      return String.format("(> %s %s)", value1, value2);
   }
}
