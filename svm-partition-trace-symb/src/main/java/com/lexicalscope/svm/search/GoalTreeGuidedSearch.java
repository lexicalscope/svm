package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.JState;

public class GoalTreeGuidedSearch implements StateSearch<JState> {
   private final GoalTreeCorrespondence correspondence;

   private final SearchMetaExtractor goalExtractor;
   private final List<JState> result = new ArrayList<>();
   private GoalTreePair correspondenceUnderConsideration;
   private boolean pInitialised;
   private boolean qInitialised;
   private JState pending;

   private GuidedSearchState side;

   public GoalTreeGuidedSearch(
         final GoalTreeCorrespondence correspondence,
         final SearchMetaExtractor goalExtractor,
         final Randomiser randomiser) {
      this.correspondence = correspondence;
      this.goalExtractor = goalExtractor;
      side = new GuidedSearchInitialState(randomiser);
   }

   @Override public JState pendingState() {
      if(pending == null) {
         return switchSides();
      }
      return pending;
   }

   private JState switchSides() {
      side.searchedSide(correspondence, correspondenceUnderConsideration);

      while(side.searchMore(correspondence)) {
         side = side.nextSide();
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

   @Override public void fork(final JState[] states) {
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

   @Override public JState firstResult() {
      return result.get(0);
   }

   @Override public Collection<JState> results() {
      return result;
   }

   @Override public void consider(final JState state) {
      if(!pInitialised) {
         goalExtractor.configureInitial(state);
         correspondence.pInitial(state);
         pInitialised = true;
      } else if(!qInitialised) {
         goalExtractor.configureInitial(state);
         correspondence.qInitial(state);
         qInitialised = true;
      } else {
         throw new IllegalStateException("only 2 initial states can be considered, not " + state);
      }
   }
}
