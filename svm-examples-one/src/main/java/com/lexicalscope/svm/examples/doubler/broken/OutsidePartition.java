package com.lexicalscope.svm.examples.doubler.broken;

public class OutsidePartition {
   public int entry(final int x) {
      return new InsidePartition().doubleIt(x);
   }
}