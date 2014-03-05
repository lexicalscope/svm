package com.lexicalscope.svm.examples.icompare.broken;


public class OutsidePartition {
   public int entry(final int x, final int y) {
      return new InsidePartition().compare(x, y);
   }

   public static int callSomeMethods(final int x, final int y) {
      return new OutsidePartition().entry(x, y);
   }
}