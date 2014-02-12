package com.lexicalscope.svm.partition.trace.symb.search.fakes;

import com.lexicalscope.svm.partition.trace.symb.PartitionStatePairs;

public class PStrategy implements ExecutionStrategy {
   private final PartitionStatePairs<FakeState> pairs;

   public PStrategy(final PartitionStatePairs<FakeState> pairs) {
      this.pairs = pairs;
   }

   @Override public void backtrack() {
   }

   @Override public void fork(final FakeState left, final FakeState right) {
      pairs.pexecution(left, right);
   }
}