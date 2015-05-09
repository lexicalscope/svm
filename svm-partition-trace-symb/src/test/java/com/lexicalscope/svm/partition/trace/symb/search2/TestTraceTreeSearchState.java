package com.lexicalscope.svm.partition.trace.symb.search2;

import static com.lexicalscope.svm.partition.trace.symb.search2.TestTraceTreeSearchState.TraceTreeSearchState.currentlySearching;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import com.lexicalscope.svm.search2.TraceTree;

public class TestTraceTreeSearchState {
   public static class TraceTreeSearchState {
      private final TraceTree root;

      public TraceTreeSearchState(final TraceTree root) {
         this.root = root;
      }

      protected TraceTree currentNode() {
         return root;
      }

      public static Matcher<? super TraceTreeSearchState> currentlySearching(final TraceTree tt) {
         return currentlySearching(equalTo(tt));
      }

      private static Matcher<TraceTreeSearchState> currentlySearching(final Matcher<TraceTree> equalTo) {
         return new FeatureMatcher<TraceTreeSearchState, TraceTree>(equalTo, "current tree node being searched", "current tree node") {
            @Override protected TraceTree featureValueOf(final TraceTreeSearchState actual) {
               return actual.currentNode();
            }
         };
      }
   }

   TraceTree tt = new TraceTree();
   TraceTreeSearchState ss = new TraceTreeSearchState(tt);

   @Test public void initialNodeIsRootNode() {
      assertThat(ss, currentlySearching(tt));
   }
}
