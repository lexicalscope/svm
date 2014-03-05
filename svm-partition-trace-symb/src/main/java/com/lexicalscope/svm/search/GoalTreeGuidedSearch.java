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
   private final Randomiser randomiser;
   private boolean searchingQ = true;
   private GoalTreePair<T, S> correspondenceUnderConsideration;
   private boolean pInitialised;
   private boolean qInitialised;
   private S pending;

   private SearchState side = new InitialState();
   private final SearchState searchingP = new SearchingP();
   private final SearchState searchingQx = new SearchingQ();

   private interface SearchState {
      void searchedSide();

      boolean searchMore();

      SearchState nextState();

      boolean isOpen();

      void searchNode();
   }

   private class InitialState implements SearchState {
      @Override public void searchedSide() {
         correspondenceUnderConsideration = correspondence.randomOpenChild(randomiser);
      }

      @Override public boolean searchMore() {
         return true;
      }

      @Override public SearchState nextState() {
         return searchingP;
      }

      @Override public boolean isOpen() {
         throw new UnsupportedOperationException();
      }

      @Override public void searchNode() {
         throw new UnsupportedOperationException();
      }}

   private class SearchingP implements SearchState {
      @Override public void searchedSide() {
         // TODO Auto-generated method stub
      }

      @Override public boolean searchMore() {
         return true;
      }

      @Override public SearchState nextState() {
         return searchingQx;
      }

      @Override public boolean isOpen() {
         return correspondenceUnderConsideration.psideIsOpen();
      }

      @Override public void searchNode() {
         pending = correspondenceUnderConsideration.openPNode(randomiser);
      }}

   private class SearchingQ implements SearchState {
      @Override public void searchedSide() {
         if(correspondenceUnderConsideration.isOpen()) {
            correspondence.stillOpen(correspondenceUnderConsideration);
         }
      }

      @Override public boolean searchMore() {
         return correspondence.hasOpenChildren();
      }

      @Override public SearchState nextState() {
         correspondenceUnderConsideration = correspondence.randomOpenChild(randomiser);
         return searchingP;
      }

      @Override public boolean isOpen() {
         return correspondenceUnderConsideration.qsideIsOpen();
      }

      @Override public void searchNode() {
         pending = correspondenceUnderConsideration.openQNode(randomiser);
      }};

   public GoalTreeGuidedSearch(
         final GoalTreeCorrespondence<T, S> correspondence,
         final GoalExtractor<T, S> goalExtractor,
         final Randomiser randomiser) {
      this.correspondence = correspondence;
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
      side.searchedSide();

      while(side.searchMore()) {
         searchingQ = !searchingQ;
         side = side.nextState();
         if(side.isOpen()) {
            side.searchNode();
            return pending;
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
      if(searchingQ) {
         correspondence.
            reachedQ(
                  correspondenceUnderConsideration,
                  goalExtractor.goal(pending),
                  pending,
                  goalExtractor.pc(pending));
      } else {
         correspondence.
            reachedP(
                  correspondenceUnderConsideration,
                  goalExtractor.goal(pending),
                  pending,
                  goalExtractor.pc(pending));
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
