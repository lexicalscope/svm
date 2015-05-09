package com.lexicalscope.svm.search2;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class TraceTreeTracker implements TraceTreeObserver {
   private final Collection<TraceTree> pstatesAvailable = new ArrayList<TraceTree>();
   private final Collection<TraceTree> qstatesAvailable = new ArrayList<TraceTree>();

   @Override public void pstateAvailable(final TraceTree traceTree) {
      pstatesAvailable.add(traceTree);
   }

   @Override public void pstateUnavailable(final TraceTree traceTree) {
      pstatesAvailable.remove(traceTree);
   }

   @Override public void qstateAvailable(final TraceTree traceTree) {
      qstatesAvailable.add(traceTree);
   }

   @Override public void qstateUnavailable(final TraceTree traceTree) {
      qstatesAvailable.remove(traceTree);
   }

   protected Collection<TraceTree> pstatesAvailable() {
      return pstatesAvailable;
   }

   public static FeatureMatcher<TraceTreeTracker, Collection<TraceTree>> hasPstatesAvailable(final Matcher<? super Collection<TraceTree>> contains) {
      return new FeatureMatcher<TraceTreeTracker, Collection<TraceTree>>(contains, "tree with p states available", "pStates available") {
         @Override protected Collection<TraceTree> featureValueOf(final TraceTreeTracker actual) {
            return actual.pstatesAvailable();
         }
      };
   }
}