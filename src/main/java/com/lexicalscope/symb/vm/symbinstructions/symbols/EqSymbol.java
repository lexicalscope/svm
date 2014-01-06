package com.lexicalscope.symb.vm.symbinstructions.symbols;

public final class EqSymbol extends AbstractUnarySymbol {
   public EqSymbol(final ISymbol val) {
      super(val);
   }

   @Override
   public String toString() {
      return String.format("(== %s 0)", val);
   }

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.eq(val);
   }
}
