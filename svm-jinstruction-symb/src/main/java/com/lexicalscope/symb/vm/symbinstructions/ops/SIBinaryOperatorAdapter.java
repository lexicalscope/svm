package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class SIBinaryOperatorAdapter implements BinaryOperator {
   private final SIBinaryOperator op;

   public SIBinaryOperatorAdapter(final SIBinaryOperator op) {
      this.op = op;
   }

   @Override public Object eval(final Object value1, final Object value2) {
      if(value1 instanceof Integer && value2 instanceof Integer) {
         return op.eval((Integer) value1, (Integer) value2);
      } else {
         ISymbol svalue1;
         ISymbol svalue2;
         if(value1 instanceof Integer) {
            svalue1 = new IConstSymbol((int) value1);
            svalue2 = (ISymbol) value2;
         } else if (value2 instanceof Integer) {
            svalue1 = (ISymbol) value1;
            svalue2 = new IConstSymbol((int) value2);
         } else {
            svalue1 = (ISymbol) value1;
            svalue2 = (ISymbol) value2;
         }
         return op.eval(svalue1, svalue2);
      }
   }

   @Override public String toString() {
      return op.toString();
   }
}
