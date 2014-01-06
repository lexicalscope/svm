package com.lexicalscope.symb.vm.concinstructions.predicates;


public class ICmpNe implements ICmpOp {
   @Override public String toString() {
      return "IF_ICMPNE";
   }

   @Override public Boolean cmp(final int value1, final int value2) {
      return value1 != value2;
   }
}
