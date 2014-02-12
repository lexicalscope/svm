package com.lexicalscope.svm.partition.trace.symb.search;

public class FakeExecuteToBranch implements FakeExecution {
   private final FakeExecution left;
   private final FakeExecution right;

   public FakeExecuteToBranch(final FakeExecution left, final FakeExecution right) {
      this.left = left;
      this.right = right;
   }
}
