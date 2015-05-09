package com.lexicalscope.svm.search2;

import java.util.Collection;

import com.lexicalscope.svm.search.GuidedSearchObserver;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class TreeSearch2 implements StateSearch<JState> {
   private final GuidedSearchObserver observer;
   private final FeasibilityChecker feasibilityChecker;

   public TreeSearch2(
         final GuidedSearchObserver observer,
         final FeasibilityChecker feasibilityChecker,
         final Randomiser randomiser) {
      this.observer = observer;
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override public JState pendingState() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override public void reachedLeaf() {
      // TODO Auto-generated method stub

   }

   @Override public void fork(final JState parent, final JState[] states) {
      // TODO Auto-generated method stub

   }

   @Override public void goal() {
      // TODO Auto-generated method stub

   }

   @Override public JState firstResult() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override public Collection<JState> results() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override public void consider(final JState state) {
      // TODO Auto-generated method stub

   }
}
