package com.lexicalscope.svm.j.instruction.symbolic.ops;

import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOperator;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;

public final class SIConstOperator implements NullaryOperator {
   private final IConstSymbol val;

   public SIConstOperator(final int val) {
      this.val = new IConstSymbol(val);
   }

   @Override
   public Object eval() {
      return val;
   }

   @Override
   public String toString() {
      return "ICONST_" + val;
   }
}