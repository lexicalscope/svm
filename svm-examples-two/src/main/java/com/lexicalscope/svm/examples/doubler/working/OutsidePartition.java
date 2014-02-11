package com.lexicalscope.svm.examples.doubler.working;


public class OutsidePartition {
   public int entry(final int x) {
      return new InsidePartition().doubleIt(x);
   }

   public static int callSomeMethods(final int x) {
      return new OutsidePartition().entry(x);
   }
}