package com.lexicalscope.svm.partition.trace.symb.tree;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.search.Randomiser;

public interface GoalMap<T, N> extends Iterable<N> {
   public interface SubtreeFactory<N> { N create(); }

   void put(T goal, N node);
   N get(T goal);
   N get(T goal, SubtreeFactory<N> factory);
   N getRandom(Randomiser randomiser);

   boolean containsMatching(Matcher<? super N> childMatcher);
   boolean containsGoal(T goal);
   boolean isChildForGoal(N child, T goal);

   int size();
   boolean isEmpty();
}
