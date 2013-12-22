package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.UnaryOperator;


public class INegOp implements UnaryOperator {
   @Override
   public Object eval(final Object val) {
      return -1 * (int) val;
   }

   @Override
   public String toString() {
      return "INEG";
   }
}
