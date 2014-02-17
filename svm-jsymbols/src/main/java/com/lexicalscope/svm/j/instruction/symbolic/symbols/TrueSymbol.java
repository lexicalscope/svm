package com.lexicalscope.svm.j.instruction.symbolic.symbols;


public class TrueSymbol implements BoolSymbol {
   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.tru3();
   }

   @Override public BoolSymbol and(final BoolSymbol conjunct) {
      return conjunct;
   }

   @Override public BoolSymbol or(final BoolSymbol disjunct) {
      return this;
   }

   @Override public BoolSymbol not() {
      return new FalseSymbol();
   }

   @Override public String toString() {
      return "TT";
   }
}
