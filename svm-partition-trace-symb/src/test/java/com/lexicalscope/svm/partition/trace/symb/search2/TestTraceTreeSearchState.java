package com.lexicalscope.svm.partition.trace.symb.search2;

import static com.lexicalscope.svm.partition.trace.symb.search2.TestTraceTreeSearchState.TraceTreeSearchState.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import com.lexicalscope.svm.search2.TraceTree;

public class TestTraceTreeSearchState {
   public static class TraceTreeSearchState {
      private TraceTree currentlySearching;
      private Side side;

      public TraceTreeSearchState(final TraceTree root) {
         this.currentlySearching = root;
         side = Side.QSIDE;
      }

      protected TraceTree currentNode() {
         return currentlySearching;
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

      public static Matcher<? super TraceTreeSearchState> currentSideIs(final Side side) {
         return currentSideIs(equalTo(side));
      }

      public static Matcher<? super TraceTreeSearchState> currentSideIs(final Matcher<Side> equalTo) {
         return new FeatureMatcher<TraceTreeSearchState, Side>(equalTo, "current side", "currentSide") {
            @Override protected Side featureValueOf(final TraceTreeSearchState actual) {
               return actual.currentSide();
            }
         };
      }

      protected Side currentSide() {
         return side;
      }

      public void search(final Side side, final TraceTree tt) {
         this.side = side;
         currentlySearching = tt;
      }
   }

   TraceTree tt1 = new TraceTree();
   TraceTree tt2 = new TraceTree();
   TraceTreeSearchState ss = new TraceTreeSearchState(tt1);

   @Test public void initialNodeIsRootNode() {
      assertThat(ss, currentlySearching(tt1));
      assertThat(ss, currentSideIs(Side.QSIDE));
   }

   @Test public void canSearchSomethingElse() {
      ss.search(Side.PSIDE, tt2);
      assertThat(ss, currentlySearching(tt2));
      assertThat(ss, currentSideIs(Side.PSIDE));
   }
}
