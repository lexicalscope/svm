package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.vm.j.JState;

class GuidedSearchSearchingQ<T1, S1> implements GuidedSearchState<T1, S1> {
   private final GuidedSearchSearchingP<T1, ?> searchingP;
   private final Randomiser randomiser;

   public GuidedSearchSearchingQ(final GuidedSearchSearchingP<T1, ?> searchingP, final Randomiser randomiser) {
      this.searchingP = searchingP;
      this.randomiser = randomiser;
   }

   @Override public void searchedSide(
         final GoalTreeCorrespondence<T1, JState> correspondence,
         final GoalTreePair pair) {
      if(pair.isOpen()) {
         correspondence.stillOpen(pair);
      }
   }

   @Override public boolean searchMore(final GoalTreeCorrespondence<T1, JState> correspondence) {
      return correspondence.hasOpenChildren();
   }

   @Override public GuidedSearchState<T1, ?> nextSide() {
      return searchingP;
   }

   @Override public boolean isOpen(final GoalTreePair correspondenceUnderConsideration) {
      return correspondenceUnderConsideration.qsideIsOpen();
   }

   @Override public JState pickSearchNode(final GoalTreePair correspondenceUnderConsideration) {
      return correspondenceUnderConsideration.openQNode(randomiser);
   }

   @Override public GoalTreePair pickCorrespondence(
         final GoalTreeCorrespondence<T1, JState> correspondence,
         final GoalTreePair correspondenceUnderConsideration) {
      return correspondenceUnderConsideration;
   }

   @Override public void fork(
         final GoalTreePair correspondenceUnderConsideration, final JState[] states) {
      correspondenceUnderConsideration.expandQ(states);
   }

   @Override public void goal(
         final GoalTreeCorrespondence<T1, JState> correspondence,
         final GoalTreePair parent,
         final T1 goal,
         final JState state,
         final BoolSymbol pc) {
      correspondence.reachedQ(parent, goal, state, pc);
   }
}