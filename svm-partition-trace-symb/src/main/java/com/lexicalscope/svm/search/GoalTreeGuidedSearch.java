package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
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

   private static final class InitialState<T1, S1> implements GuidedSearchState<T1, S1> {
      private final Randomiser randomiser;

      public InitialState(final Randomiser randomiser) {
         this.randomiser = randomiser;
      }

      @Override public void searchedSide(
            final GoalTreeCorrespondence<T1, S1> correspondence,
            final GoalTreePair<T1, S1> pair) {
         // nothing
      }

      @Override public boolean searchMore(final GoalTreeCorrespondence<T1, S1> correspondence) {
         return true;
      }

      @Override public GuidedSearchState<T1, S1> nextState() {
         return new SearchingP<T1, S1>(randomiser);
      }

      @Override public boolean isOpen(final GoalTreePair<T1, S1> correspondence) {
         throw new UnsupportedOperationException();
      }

      @Override public S1 pickSearchNode(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
         throw new UnsupportedOperationException();
      }

      @Override public GoalTreePair<T1, S1> pickCorrespondence(final GoalTreeCorrespondence<T1, S1> correspondence, final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
         throw new UnsupportedOperationException();
      }

      @Override public void fork(final GoalTreePair<T1, S1> correspondenceUnderConsideration, final S1[] states) {
         throw new UnsupportedOperationException();
      }

      @Override public void goal(final GoalTreeCorrespondence<T1, S1> correspondence, final GoalTreePair<T1, S1> correspondenceUnderConsideration, final T1 goal, final S1 pending, final BoolSymbol pc) {
         throw new UnsupportedOperationException();
      }}

   private static class SearchingP<T1, S1> implements GuidedSearchState<T1, S1> {
      private final GuidedSearchState<T1, S1> searchingQy;
      private final Randomiser randomiser;

      public SearchingP(final Randomiser randomiser) {
         this.randomiser = randomiser;
         searchingQy = new SearchingQ<T1, S1>(this, randomiser);
      }

      @Override public void searchedSide(
            final GoalTreeCorrespondence<T1, S1> correspondence,
            final GoalTreePair<T1, S1> pair) {
      }

      @Override public GoalTreePair<T1, S1> pickCorrespondence(
            final GoalTreeCorrespondence<T1, S1> correspondence,
            final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
         return correspondence.randomOpenChild(randomiser);
      }

      @Override public boolean searchMore(final GoalTreeCorrespondence<T1, S1> correspondence) {
         return true;
      }

      @Override public GuidedSearchState<T1, S1> nextState() {
         return searchingQy;
      }

      @Override public boolean isOpen(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
         return correspondenceUnderConsideration.psideIsOpen();
      }

      @Override public S1 pickSearchNode(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
         return correspondenceUnderConsideration.openPNode(randomiser);
      }

      @Override public void fork(
            final GoalTreePair<T1, S1> correspondenceUnderConsideration, final S1[] states) {
         correspondenceUnderConsideration.expandP(states);
      }

      @Override public void goal(
            final GoalTreeCorrespondence<T1, S1> correspondence,
            final GoalTreePair<T1, S1> parent,
            final T1 goal,
            final S1 state,
            final BoolSymbol pc) {
         correspondence.reachedP(parent, goal, state, pc);
      }}

   private static class SearchingQ<T1, S1> implements GuidedSearchState<T1, S1> {
      private final SearchingP<T1, S1> searchingP;
      private final Randomiser randomiser;

      public SearchingQ(final SearchingP<T1, S1> searchingP, final Randomiser randomiser) {
         this.searchingP = searchingP;
         this.randomiser = randomiser;
      }

      @Override public void searchedSide(
            final GoalTreeCorrespondence<T1, S1> correspondence,
            final GoalTreePair<T1, S1> pair) {
         if(pair.isOpen()) {
            correspondence.stillOpen(pair);
         }
      }

      @Override public boolean searchMore(final GoalTreeCorrespondence<T1, S1> correspondence) {
         return correspondence.hasOpenChildren();
      }

      @Override public GuidedSearchState<T1, S1> nextState() {
         return searchingP;
      }

      @Override public boolean isOpen(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
         return correspondenceUnderConsideration.qsideIsOpen();
      }

      @Override public S1 pickSearchNode(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
         return correspondenceUnderConsideration.openQNode(randomiser);
      }

      @Override public GoalTreePair<T1, S1> pickCorrespondence(
            final GoalTreeCorrespondence<T1, S1> correspondence,
            final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
         return correspondenceUnderConsideration;
      }

      @Override public void fork(
            final GoalTreePair<T1, S1> correspondenceUnderConsideration, final S1[] states) {
         correspondenceUnderConsideration.expandQ(states);
      }

      @Override public void goal(
            final GoalTreeCorrespondence<T1, S1> correspondence,
            final GoalTreePair<T1, S1> parent,
            final T1 goal,
            final S1 state,
            final BoolSymbol pc) {
         correspondence.reachedQ(parent, goal, state, pc);
      }};

   public GoalTreeGuidedSearch(
         final GoalTreeCorrespondence<T, S> correspondence,
         final GoalExtractor<T, S> goalExtractor,
         final Randomiser randomiser) {
      this.correspondence = correspondence;
      this.goalExtractor = goalExtractor;
      side = new InitialState<>(randomiser);
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
