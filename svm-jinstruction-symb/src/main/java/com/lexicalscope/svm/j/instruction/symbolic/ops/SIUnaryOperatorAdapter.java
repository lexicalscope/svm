package com.lexicalscope.svm.j.instruction.symbolic.ops;

import com.lexicalscope.svm.j.instruction.concrete.ops.UnaryOperator;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public class SIUnaryOperatorAdapter implements UnaryOperator {
   private final SIUnaryOperator op;

   public SIUnaryOperatorAdapter(final SIUnaryOperator op) {
      this.op = op;
   }

   @Override public Object eval(final Object value1) {
      if(value1 instanceof Integer) {
         return op.eval((Integer) value1);
      } else {
         return op.eval((ISymbol) value1);
      }
   }

   @Override public String toString() {
      return op.toString();
   }
}
