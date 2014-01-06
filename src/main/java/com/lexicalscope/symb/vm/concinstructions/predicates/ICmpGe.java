package com.lexicalscope.symb.vm.concinstructions.predicates;


public class ICmpGe implements ICmpOp {
   @Override public Boolean cmp(final int value1, final int value2) {
      return value1 >= value2;
   }

   @Override public String toString() {
      return "IF_ICMPGE";
   }
}
