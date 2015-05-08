package com.lexicalscope.svm.partition.trace.symb.tree;

import com.lexicalscope.svm.partition.trace.Trace;

public class ObjectGoalMapFactory implements GoalMapFactory<Trace> {
   @Override public <N> GoalMap<Trace, N> newGoalMap() {
      return new ObjectGoalMap<N>();
   }
}
