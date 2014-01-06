package com.lexicalscope.symb.vm.symbinstructions.symbols;

public class ICmpLeSymbol implements ISymbol {
   private final ISymbol value1;
   private final ISymbol value2;

   public ICmpLeSymbol(final ISymbol value1, final ISymbol value2) {
      this.value1 = value1;
      this.value2 = value2;
   }

   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.le(value1, value2);
   }
}
