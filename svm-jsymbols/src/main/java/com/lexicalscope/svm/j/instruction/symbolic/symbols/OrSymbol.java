package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class OrSymbol extends AbstractBoolSymbol {
   private final BoolSymbol left;
   private final BoolSymbol right;

   public OrSymbol(final BoolSymbol left, final BoolSymbol right) {
      this.left = left;
      this.right = right;
   }

   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.or(left, right);
   }
}
