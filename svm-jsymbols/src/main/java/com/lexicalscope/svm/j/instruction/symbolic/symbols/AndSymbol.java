package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class AndSymbol implements BoolSymbol {
   private final BoolSymbol left;
   private final BoolSymbol right;

   public AndSymbol(final BoolSymbol left, final BoolSymbol right) {
      this.left = left;
      this.right = right;
   }

   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.and(left, right);
   }
}
