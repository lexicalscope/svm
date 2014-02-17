package com.lexicalscope.svm.j.instruction.symbolic.symbols;


public class TrueSymbol implements BoolSymbol {
   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.tru3();
   }

   @Override public String toString() {
      return "TT";
   }
}
