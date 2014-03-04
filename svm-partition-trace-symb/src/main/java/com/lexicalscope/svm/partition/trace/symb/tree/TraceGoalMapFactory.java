package com.lexicalscope.svm.partition.trace.symb.tree;

import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class TraceGoalMapFactory implements GoalMapFactory<Trace> {
    private final FeasibilityChecker checker;

   public TraceGoalMapFactory(final FeasibilityChecker checker) {
      this.checker = checker;
   }

   @Override public <N> GoalMap<Trace, N> newGoalMap() {
      return new TraceGoalMap<>(checker);
   }
}
