package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public final class LeSymbol extends AbstractUnaryBoolSymbol {
   public LeSymbol(final ISymbol val) {
      super(val);
   }

   @Override
   public String toString() {
      return String.format("(<= %s 0)", val);
   }

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.le(val);
   }
}
