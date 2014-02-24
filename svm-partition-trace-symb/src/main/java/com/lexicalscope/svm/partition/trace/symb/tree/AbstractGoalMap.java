package com.lexicalscope.svm.partition.trace.symb.tree;

import org.hamcrest.Matcher;

public abstract class AbstractGoalMap<T, N> implements GoalMap<T, N> {
   @Override public boolean containsMatching(final Matcher<? super N> childMatcher) {
      for (final N child : this) {
         if(childMatcher.matches(child)) {
            return true;
         }
      }
      return false;
   }
}
