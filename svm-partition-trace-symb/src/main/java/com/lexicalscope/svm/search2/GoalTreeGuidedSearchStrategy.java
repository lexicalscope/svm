package com.lexicalscope.svm.search2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.StateSearch;

public class GoalTreeGuidedSearchStrategy<T, S> implements StateSearch<S> {
   private final GoalTreeCorrespondence<T, S> correspondence;
   private final List<S> result = new ArrayList<>();
   private final Randomiser randomiser;
   private boolean searchingQ = true;
   private GoalTreePair<T, S> correspodenceUnderConsideration;
   private boolean pInitialised;
   private boolean qInitialised;
   private S pending;

   public GoalTreeGuidedSearchStrategy(
         final GoalTreeCorrespondence<T, S> correspondence,
         final Randomiser randomiser) {
      this.correspondence = correspondence;
      this.randomiser = randomiser;
   }

   @Override public S pendingState() {
      if(pending == null) {
         return switchSides();
      }
      return pending;
   }

   private S switchSides() {
      if(searchingQ) {
         searchingQ = false;
         correspodenceUnderConsideration = correspondence.randomOpenCorrespondence(randomiser);
         return pending = correspodenceUnderConsideration.openPNode(randomiser);
      } else {
         searchingQ = true;
         return pending = correspodenceUnderConsideration.openQNode(randomiser);
      }
   }

   @Override public void reachedLeaf() {
      result.add(pending);
      switchSides();
   }

   @Override public void fork(final S[] states) {
      if(searchingQ) {
         correspodenceUnderConsideration.expandQ(states);
      } else {
         correspodenceUnderConsideration.expandP(states);
      }
      switchSides();
   }

   @Override public void goal() {
      // TODO Auto-generated method stub

   }

   @Override public S firstResult() {
      return result.get(0);
   }

   @Override public Collection<S> results() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override public void consider(final S state) {
      if(!pInitialised) {
         correspondence.pInitial(state);
         pInitialised = true;
      } else if(!qInitialised) {
         correspondence.qInitial(state);
         qInitialised = true;
      } else {
         throw new IllegalStateException("only 2 initial states can be considered, not " + state);
      }
   }
}
