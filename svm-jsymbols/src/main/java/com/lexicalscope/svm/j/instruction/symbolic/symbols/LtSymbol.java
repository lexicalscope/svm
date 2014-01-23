package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public final class LtSymbol extends AbstractUnaryBoolSymbol {
   public LtSymbol(final ISymbol val) {
      super(val);
   }

   @Override
   public String toString() {
      return String.format("(< %s 0)", val);
   }

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.lt(val);
   }
}
