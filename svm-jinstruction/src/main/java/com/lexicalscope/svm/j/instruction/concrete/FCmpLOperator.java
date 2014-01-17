package com.lexicalscope.svm.j.instruction.concrete;



public class FCmpLOperator implements BinaryOperator {
   @Override public Object eval(final Object left, final Object right) {
      final float leftf = (float) left;
      final float rightf = (float) right;
      if(leftf == rightf) {
         return 0;
      }

      // This is funky due to possibility of NAN
      final boolean greaterThan = leftf > rightf;
      if(!greaterThan) {
         return -1;
      } else {
         return 1;
      }
   }

   @Override public String toString() {
      return "FCMPG";
   }
}
