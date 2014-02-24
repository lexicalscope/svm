package com.lexicalscope.svm.partition.trace.symb.tree;

import org.hamcrest.Matcher;

public interface GoalMap<T, N> extends Iterable<N> {
   void put(T goal, N node);
   boolean isEmpty();
   boolean containsMatching(Matcher<? super N> childMatcher);
   int size();
}
