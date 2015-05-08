package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.vm.j.JState;

class GuidedSearchSearchingP<T1, S1> implements GuidedSearchState<T1, S1> {
   private final GuidedSearchState<T1, JState> searchingQy;
   private final Randomiser randomiser;

   public GuidedSearchSearchingP(final Randomiser randomiser) {
      this.randomiser = randomiser;
      searchingQy = new GuidedSearchSearchingQ<T1, JState>(this, randomiser);
   }

   @Override public void searchedSide(
         final GoalTreeCorrespondence<T1, JState> correspondence,
         final GoalTreePair pair) {
   }

   @Override public GoalTreePair pickCorrespondence(
         final GoalTreeCorrespondence<T1, JState> correspondence,
         final GoalTreePair correspondenceUnderConsideration) {
      return correspondence.randomOpenChild(randomiser);
   }

   @Override public boolean searchMore(final GoalTreeCorrespondence<T1, JState> correspondence) {
      return true;
   }

   @Override public GuidedSearchState<T1, JState> nextSide() {
      return searchingQy;
   }

   @Override public boolean isOpen(final GoalTreePair correspondenceUnderConsideration) {
      return correspondenceUnderConsideration.psideIsOpen();
   }

   @Override public JState pickSearchNode(final GoalTreePair correspondenceUnderConsideration) {
      return correspondenceUnderConsideration.openPNode(randomiser);
   }

   @Override public void fork(
         final GoalTreePair correspondenceUnderConsideration, final JState[] states) {
      correspondenceUnderConsideration.expandP(states);
   }

   @Override public void goal(
         final GoalTreeCorrespondence<T1, JState> correspondence,
         final GoalTreePair parent,
         final T1 goal,
         final JState state,
         final BoolSymbol pc) {
      correspondence.reachedP(parent, goal, state, pc);
   }
}