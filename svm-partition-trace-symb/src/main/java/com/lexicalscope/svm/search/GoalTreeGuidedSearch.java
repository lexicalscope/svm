package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.vm.StateSearch;

public class GoalTreeGuidedSearch<T, S> implements StateSearch<S> {
   private final GoalTreeCorrespondence<T, S> correspondence;

   private final GoalExtractor<T, S> goalExtractor;
   private final List<S> result = new ArrayList<>();
   private GoalTreePair<T, S> correspondenceUnderConsideration;
   private boolean pInitialised;
   private boolean qInitialised;
   private S pending;

   private GuidedSearchState<T, S> side;

   public GoalTreeGuidedSearch(
         final GoalTreeCorrespondence<T, S> correspondence,
         final GoalExtractor<T, S> goalExtractor,
         final Randomiser randomiser) {
      this.correspondence = correspondence;
      this.goalExtractor = goalExtractor;
      side = new GuidedSearchInitialState<>(randomiser);
   }

   @Override public S pendingState() {
      if(pending == null) {
         return switchSides();
      }
      return pending;
   }

   private S switchSides() {
      side.searchedSide(correspondence, correspondenceUnderConsideration);

      while(side.searchMore(correspondence)) {
         side = side.nextState();
         correspondenceUnderConsideration =
               side.pickCorrespondence(correspondence, correspondenceUnderConsideration);

         if(side.isOpen(correspondenceUnderConsideration)) {
            return pending = side.pickSearchNode(correspondenceUnderConsideration);
         }
      }
      return pending = null;
   }

   @Override public void reachedLeaf() {
      result.add(pending);
      switchSides();
   }

   @Override public void fork(final S[] states) {
      side.fork(correspondenceUnderConsideration, states);
      switchSides();
   }

   @Override public void goal() {
      side.goal(
            correspondence,
            correspondenceUnderConsideration,
            goalExtractor.goal(pending),
            pending,
            goalExtractor.pc(pending));

      switchSides();
   }

   @Override public S firstResult() {
      return result.get(0);
   }

   @Override public Collection<S> results() {
      return result;
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
