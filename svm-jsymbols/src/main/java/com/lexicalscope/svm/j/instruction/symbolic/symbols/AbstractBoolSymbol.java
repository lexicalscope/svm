package com.lexicalscope.svm.j.instruction.symbolic.symbols;


public abstract class AbstractBoolSymbol implements BoolSymbol {
   @Override public BoolSymbol and(final BoolSymbol conjunct) {
      return new AndSymbol(this, conjunct);
   }

   @Override public BoolSymbol or(final BoolSymbol disjunct) {
      return new OrSymbol(this, disjunct);
   }

   @Override public BoolSymbol not() {
      return null;
   }
}
