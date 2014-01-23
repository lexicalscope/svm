package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class IArrayZeroedSymbol implements IArraySymbol {
   @Override
   public String toString() {
      return String.format("#iaz");
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      return obj == this || obj != null && obj.getClass().equals(this.getClass());
   }

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.intArrayZeroed();
   }
}
