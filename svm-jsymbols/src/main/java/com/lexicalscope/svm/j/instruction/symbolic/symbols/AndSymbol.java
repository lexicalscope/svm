package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class AndSymbol extends AbstractBoolSymbol {
   private final BoolSymbol left;
   private final BoolSymbol right;

   public AndSymbol(final BoolSymbol left, final BoolSymbol right) {
      assert left != null && right != null;
      this.left = left;
      this.right = right;
   }

   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.and(left, right);
   }

   @Override public String toString() {
      return String.format("(AND %s %s)", left, right);
   }
}
