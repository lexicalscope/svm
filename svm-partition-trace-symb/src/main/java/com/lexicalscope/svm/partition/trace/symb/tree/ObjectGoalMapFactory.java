package com.lexicalscope.svm.partition.trace.symb.tree;

public class ObjectGoalMapFactory implements GoalMapFactory<Object> {
   @Override public <N> GoalMap<Object, N> newGoalMap() {
      return new ObjectGoalMap<N>();
   }
}
