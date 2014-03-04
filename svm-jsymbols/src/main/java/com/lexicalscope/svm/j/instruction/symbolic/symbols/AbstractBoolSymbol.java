package com.lexicalscope.svm.j.instruction.symbolic.symbols;


public abstract class AbstractBoolSymbol implements BoolSymbol {
   @Override public final BoolSymbol and(final BoolSymbol conjunct) {
      return new AndSymbol(this, conjunct);
   }

   @Override public final BoolSymbol or(final BoolSymbol disjunct) {
      return new OrSymbol(this, disjunct);
   }

   @Override public final BoolSymbol not() {
      return new NotSymbol(this);
   }
}
