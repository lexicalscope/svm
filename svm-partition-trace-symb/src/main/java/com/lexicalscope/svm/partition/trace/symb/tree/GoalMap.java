package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.Collection;

import org.hamcrest.Matcher;

public interface GoalMap<T, N> extends Iterable<N> {
   public interface SubtreeFactory<N> { N create(); }

   void put(T goal, N node);
   N get(T goal);
   N get(T goal, SubtreeFactory<N> factory);

   boolean containsMatching(Matcher<? super N> childMatcher);
   boolean containsGoal(T goal);
   boolean isChildForGoal(N child, T goal);

   Collection<N> values();
   int size();
   boolean isEmpty();
}
