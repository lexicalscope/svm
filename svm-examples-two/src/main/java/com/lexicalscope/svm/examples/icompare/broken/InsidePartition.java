package com.lexicalscope.svm.examples.icompare.broken;

public class InsidePartition {
   public int compare(final int x, final int y) {
      if(x < y) {
         return -1;
      } else if (x >= y) {
         return 1;
      } else {
         return 0;
      }
   }
}
