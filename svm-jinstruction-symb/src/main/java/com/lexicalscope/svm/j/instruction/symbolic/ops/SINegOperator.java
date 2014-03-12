package com.lexicalscope.svm.j.instruction.symbolic.ops;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.INegSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public class SINegOperator implements SIUnaryOperator {
   @Override
   public String toString() {
      return "S INEG";
   }

   @Override public Integer eval(final Integer value) {
      return -1 * value;
   }

   @Override public ISymbol eval(final ISymbol svalue) {
      return new INegSymbol(svalue);
   }
}
