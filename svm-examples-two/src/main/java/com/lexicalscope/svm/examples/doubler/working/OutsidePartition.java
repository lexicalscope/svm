package com.lexicalscope.svm.examples.doubler.working;

public class OutsidePartition {
   public int entry(final int x) {
      return new InsidePartition().doubleIt(x);
   }
}