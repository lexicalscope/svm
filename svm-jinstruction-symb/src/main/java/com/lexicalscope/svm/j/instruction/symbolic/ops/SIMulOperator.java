package com.lexicalscope.svm.j.instruction.symbolic.ops;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.MulSymbol;

public class SIMulOperator implements SIBinaryOperator {
   @Override
   public String toString() {
      return "S IMUL";
   }

   @Override
   public Integer eval(final Integer left, final Integer right) {
      return left * right;
   }

   @Override public ISymbol eval(final ISymbol svalue1, final ISymbol svalue2) {
      return new MulSymbol(svalue1, svalue2);
   }
}
