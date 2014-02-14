package com.lexicalscope.svm.partition.trace.symb.search.fakes;

import com.lexicalscope.svm.partition.trace.symb.PartitionStatePairs;

public class QStrategy implements ExecutionStrategy {
   private final PartitionStatePairs<FakeState> pairs;

   public QStrategy(final PartitionStatePairs<FakeState> pairs) {
      this.pairs = pairs;
   }

   @Override public void backtrack() {
      pairs.backtrack();
   }

   @Override public void fork(final FakeState left, final FakeState right) {
      pairs.qexecution(left, right);
   }

   @Override public void goal(final FakeState successor) {
      pairs.qexecution(successor);
   }
}
