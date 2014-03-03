package com.lexicalscope.svm.search2;

import java.util.Collection;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.StateSearch;

public class GoalTreeGuidedSearchStrategy<T, S> implements StateSearch<S> {
   private final GoalTreeCorrespondence<T, S> correspondence;
   private final Randomiser randomiser;
   private boolean pNext = true;
   private GoalTreePair<T, S> correspodenceUnderConsideration;

   public GoalTreeGuidedSearchStrategy(
         final GoalTreeCorrespondence<T, S> correspondence,
         final Randomiser randomiser) {
      this.correspondence = correspondence;
      this.randomiser = randomiser;
   }

   @Override public S pendingState() {
      if(pNext) {
         pNext = false;
         correspodenceUnderConsideration = correspondence.randomOpenCorrespondence(randomiser);
         return correspodenceUnderConsideration.openPNode(randomiser);
      } else {
         pNext = true;
         return correspodenceUnderConsideration.openQNode(randomiser);
      }
   }

   @Override public void reachedLeaf() {
      // TODO Auto-generated method stub

   }

   @Override public void fork(final S[] states) {
      // TODO Auto-generated method stub

   }

   @Override public void goal() {
      // TODO Auto-generated method stub

   }

   @Override public S firstResult() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override public Collection<S> results() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override public void consider(final S state) {

   }
}
