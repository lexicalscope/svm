package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.vm.StateSearch;

public class GoalTreeGuidedSearch<T, S> implements StateSearch<S> {
   private final GoalTreeCorrespondence<T, S> correspondence;
   private final ListRandomPool<GoalTreePair<T, S>> openChildren;

   private final GoalExtractor<T, S> goalExtractor;
   private final List<S> result = new ArrayList<>();
   private final Randomiser randomiser;
   private boolean searchingQ = true;
   private GoalTreePair<T, S> correspondenceUnderConsideration;
   private boolean pInitialised;
   private boolean qInitialised;
   private S pending;

   public GoalTreeGuidedSearch(
         final GoalTreeCorrespondence<T, S> correspondence,
         final GoalExtractor<T, S> goalExtractor,
         final Randomiser randomiser) {
      this.correspondence = correspondence;
      openChildren = new ListRandomPool<GoalTreePair<T, S>>(correspondence.children());
      this.goalExtractor = goalExtractor;
      this.randomiser = randomiser;
   }

   @Override public S pendingState() {
      if(pending == null) {
         return switchSides();
      }
      return pending;
   }

   private S switchSides() {
      if(searchingQ &&
            correspondenceUnderConsideration != null &&
            correspondenceUnderConsideration.isOpen()) {
         openChildren.add(correspondenceUnderConsideration);
      }

      while(!searchingQ || !openChildren.isEmpty()) {
         searchingQ = !searchingQ;

         if(!searchingQ) {
            correspondenceUnderConsideration = openChildren.randomElement(randomiser);
         }

         if(!searchingQ && correspondenceUnderConsideration.psideIsOpen()) {
            return pending = correspondenceUnderConsideration.openPNode(randomiser);
         }

         if(searchingQ && correspondenceUnderConsideration.qsideIsOpen()) {
            return pending = correspondenceUnderConsideration.openQNode(randomiser);
         }
      }
      return pending = null;
   }



   @Override public void reachedLeaf() {
      result.add(pending);
      switchSides();
   }

   @Override public void fork(final S[] states) {
      if(searchingQ) {
         correspondenceUnderConsideration.expandQ(states);
      } else {
         correspondenceUnderConsideration.expandP(states);
      }
      assert correspondenceUnderConsideration.isOpen();
      switchSides();
   }

   @Override public void goal() {
      final GoalTreePair<T, S> newPair;
      if(searchingQ) {
         newPair = correspondence.
            reachedQ(
                  correspondenceUnderConsideration,
                  goalExtractor.goal(pending),
                  pending,
                  goalExtractor.pc(pending));
      } else {
         newPair = correspondence.
            reachedP(
                  correspondenceUnderConsideration,
                  goalExtractor.goal(pending),
                  pending,
                  goalExtractor.pc(pending));
      }
      if(newPair != null) {
         openChildren.add(newPair);
      }
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
