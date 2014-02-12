package com.lexicalscope.svm.partition.trace.symb.search.fakes;

public class FakeExecuteToBranch implements FakeState {
   private final FakeState left;
   private final FakeState right;

   public FakeExecuteToBranch(final FakeState left, final FakeState right) {
      this.left = left;
      this.right = right;
   }

   @Override public FakeExecuteToBranch execute(final ExecutionStrategy strategy) {
      strategy.fork(left, right);
      return this;
   }
}
