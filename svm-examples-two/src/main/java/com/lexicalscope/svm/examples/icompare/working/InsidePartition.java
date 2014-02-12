package com.lexicalscope.svm.examples.icompare.working;

public class InsidePartition {
   public int compare(final int x, final int y) {
      if(x == y) {
         return 0;
      } else if (x > y) {
         return 1;
      } else {
         return -1;
      }
   }
}
