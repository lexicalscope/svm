package com.lexicalscope.svm.partition.trace.symb.tree;


public interface GoalMapFactory<T> {
   <N> GoalMap<T, N> newGoalMap();
}
